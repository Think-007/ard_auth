/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2017年10月26日	| LPF 	| 	create the file                       
 */

package com.thinker.auth.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.domain.UserInfoDetail;
import com.thinker.auth.domain.UserRegistParam;
import com.thinker.auth.domain.result.LoginResult;
import com.thinker.auth.exception.PassWordErrorException;
import com.thinker.auth.exception.TelNumberRepeatException;
import com.thinker.auth.exception.UserLockException;
import com.thinker.auth.exception.UserNameRepeatException;
import com.thinker.auth.exception.UserNotExistException;
import com.thinker.auth.service.AuthUserService;
import com.thinker.auth.service.UserInfoService;
import com.thinker.auth.service.UserRegistService;
import com.thinker.auth.util.Redis;
import com.thinker.auth.util.TokenUtil;
import com.thinker.creator.domain.ProcessResult;
import com.thinker.security.RSAEncrypt;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;

/**
 * 
 * 登录接口
 * 
 * <p>
 * 类详细描述
 * </p>
 * 
 * @author LPF
 * 
 */
@RestController
@RequestMapping("/auth/gate")
public class GateController {

	private static final Logger logger = LoggerFactory
			.getLogger(GateController.class);

	// 随机盐值
	@Value("${shiro.salt}")
	private String saltStr;
	// 加盐次数
	@Value("${salt.hashIterations}")
	private int hashIterations;

	// 用户注册业务
	@Resource
	private UserRegistService userRegistService;

	// 用户登录鉴权业务
	@Resource
	private AuthUserService authUserService;

	// 用户信息业务
	@Resource
	private UserInfoService userInfoService;

	/**
	 * 用户注册接口 手机号_密码_昵称
	 * 
	 * @param registInfo
	 * @param smsCode
	 * @return
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ProcessResult registUser(String registInfo, String smsCode) {
		ArdLog.info(logger, "enter registUser ", null, "userRegistParam: "
				+ registInfo + "smsCode: " + smsCode);
		ProcessResult processResult = new ProcessResult();

		try {

			// 解析用户注册信息密文
			String[] userInfo = authUserService.decryptReqStr(registInfo);

			String telnum = userInfo[0];
			String password = userInfo[1];
			String userName = userInfo[2];

			UserRegistParam userRegistParam = new UserRegistParam();
			userRegistParam.setTelNumber(telnum);
			userRegistParam.setPassword(password);
			userRegistParam.setUserName(userName);

			// 1.校验登录信息
			String redisSmsCode = (String) Redis.redis.get(telnum + "_auth");

			if (smsCode == null || !smsCode.equals(redisSmsCode)) {

				processResult.setRetCode(ArdError.SMS_CODE_ERROR);
				return processResult;
			}

			// 2.密码加盐
			ArdLog.debug(logger, "registUser", null, "salt: " + saltStr
					+ "hashIterations: " + hashIterations);
			Md5Hash mh = new Md5Hash(userRegistParam.getPassword(), saltStr,
					hashIterations);
			System.out.println(mh.toString());
			userRegistParam.setPassword(mh.toString());

			// 3.创建用户,并绑定信息,默认roleid为0 ，是普通用户
			ArdUserRole ardUserRole = new ArdUserRole();
			userRegistService.regitsUser(userRegistParam, saltStr, ardUserRole);
			// 删除smscode
			Redis.redis.remove(telnum + "_auth");
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
		} catch (UserNameRepeatException e) {

			processResult.setRetCode(ArdError.NAME_REPEAT);
			processResult.setRetMsg("昵称重复");
			// ArdLog.error(logger, "registUser error username used", null,
			// null,
			// e);
			// e.printStackTrace();
		} catch (TelNumberRepeatException e1) {

			processResult.setRetCode(ArdError.TEL_NUM_REGISTED);
			processResult.setRetMsg("号码已注册");
			// ArdLog.error(logger, "registUser error telnum used", null, null,
			// e1);
			// e1.printStackTrace();

		} catch (Throwable t) {
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "registUser error", null, null, t);
			t.printStackTrace();
		}
		ArdLog.info(logger, "finish registUser ", null, "processresult: "
				+ processResult);

		return processResult;
	}

	/**
	 * 实际的登录代码 如果登录成功，跳转至首页；登录失败，则将失败信息反馈对用户 电话号码_密码 _设备号 加密
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/app_authentication", method = RequestMethod.POST)
	public ProcessResult doLogin(String loginInfo, String key) {

		ProcessResult processResult = new ProcessResult();

		try {
			// 解析用户登录信息密文
			String[] userInfo = authUserService.decryptReqStr(loginInfo);
			// 客户端公钥
			String publicKey = key;

			String telnum = userInfo[0];
			String password = userInfo[1];
			String deviceInfo = userInfo[2];

			ArdLog.debug(logger, "doLogin", null, " telnum : " + telnum
					+ " password : " + password + " deviceInfo : " + deviceInfo);

			// 1、用户认证
			String msg = null;

			try {

				if (authUserService.authUser(telnum, password)) {

					processResult.setRetCode(ProcessResult.SUCCESS);
					processResult.setRetMsg("ok");
					// 保持长连接的token
					String token = TokenUtil.generateToken();
					// 自动登录令牌
					String loginToken = TokenUtil.generateLoginToken(deviceInfo
							+ telnum);
					String retInfo = token + "_" + loginToken;

					// 将token用客户端给公钥加密返回给客户端
					String encryptToken = new String(RSAEncrypt.encrypt(
							RSAEncrypt.loadPublicKeyByStr(publicKey),
							retInfo.getBytes()));
					// 2、返回用户信息
					UserInfoDetail userInfoDetail = userInfoService
							.getUserInfoDetailByTelNumber(telnum);

					LoginResult loginResult = new LoginResult();
					loginResult.setUserInfoDetail(userInfoDetail);
					loginResult.setToken(encryptToken);

					processResult.setRetObj(loginResult);

					// 3、存储token和用户信息

					Redis.redis.put(userInfoDetail.getUserId(), token);

					Redis.redis.put(loginToken, userInfoDetail);

				}

			} catch (PassWordErrorException e) {
				msg = "登录密码错误. Password for account " + telnum
						+ " was incorrect.";
				processResult.setRetCode(ArdError.PASSWORD_ERROR);
				processResult.setRetMsg(msg);
				System.out.println(msg);
			} catch (UserLockException e) {
				msg = "帐号已被锁定. The account for username " + telnum
						+ " was disabled.";
				processResult.setRetCode(ArdError.USER_LOCKED);
				processResult.setRetMsg(msg);
				System.out.println(msg);
			} catch (UserNotExistException e) {
				msg = "帐号不存在. There is no user with username of " + telnum;
				processResult.setRetCode(ArdError.USER_NOT_EXIST);
				processResult.setRetMsg(msg);
				System.out.println(msg);
			}

		} catch (Throwable t) {
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "login error", null, null, t);
			t.printStackTrace();
		}

		return processResult;
	}

	/**
	 * 自动登录接口
	 * 
	 * @param autoLoginInfo
	 *            自动登录令牌用公钥加密
	 * @return
	 */
	@RequestMapping(value = "/app_auto_authentication", method = RequestMethod.POST)
	public ProcessResult autoLogin(String autoLoginInfo, String key) {

		ArdLog.debug(logger, " enter autoLogin", null, " autoLoginInfo : "

		+ autoLoginInfo);

		ProcessResult processResult = new ProcessResult();

		try {
			// 解析请求参数
			String[] reqInfo = authUserService.decryptReqStr(autoLoginInfo);
			String loginToken = reqInfo[0];
			UserInfoDetail userInfoDetail = (UserInfoDetail) Redis.redis
					.get(loginToken);

			// 长连接的token
			String token = TokenUtil.generateToken();

			String retInfo = token + "_" + loginToken;

			// 将token用客户端给公钥加密返回给客户端
			String encryptToken = new String(RSAEncrypt.encrypt(
					RSAEncrypt.loadPublicKeyByStr(key), retInfo.getBytes()));

			if (userInfoDetail == null) {
				processResult.setRetCode(ArdError.AUTO_LOGIN_TOKEN_TIME_OUT);
				processResult.setRetMsg("令牌过期");

				return processResult;
			}
			LoginResult loginResult = new LoginResult();
			loginResult.setUserInfoDetail(userInfoDetail);
			loginResult.setToken(encryptToken);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setRetObj(loginResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);

			e.printStackTrace();
		}

		return processResult;
	}

	/**
	 * 忘记密码,申请公钥，将 电话号码_新密码 加密成resetInfo参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/password_reset", method = RequestMethod.PUT)
	public ProcessResult resetPassword(HttpServletRequest request,
			HttpServletResponse response, String resetInfo, String smsCode) {

		ProcessResult processResult = new ProcessResult();

		try {
			// 解密参数信息
			String[] reqInfo = authUserService.decryptReqStr(resetInfo);

			String telNumber = reqInfo[0];
			String newPassowrd = reqInfo[1];

			String code = (String) Redis.redis.get(telNumber + "_auth");
			if (code != null && code.equals(smsCode)) {
				// 更改密码
				ArdUser ardUser = userInfoService
						.getUserInfoByTelNumber(telNumber);
				userInfoService.updateUserPassword(ardUser.getUserId(),
						newPassowrd);
				// 删除smscode
				Redis.redis.remove(telNumber + "_auth");
				processResult.setRetCode(ProcessResult.SUCCESS);
				processResult.setRetMsg("ok");
			} else {

				processResult.setRetCode(ArdError.SMS_CODE_ERROR);
			}
		} catch (Throwable t) {
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "login error", null, null, t);
			t.printStackTrace();
		}

		return processResult;

	}

	/**
	 * token 状态判定接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/tokenstatus")
	public ProcessResult retTokenStatus(HttpServletRequest request,
			HttpServletResponse response) {
		ProcessResult result = (ProcessResult) request
				.getAttribute("processResult");

		ArdLog.debug(logger, "", "", "debug");
		ArdLog.info(logger, "", "", "info");

		return result;

	}

	@RequestMapping("/testparam")
	public ProcessResult testParam(HttpServletRequest request,
			HttpServletResponse response, String telNumber, String a) {

		SortedMap<String, String> paramsMap = new TreeMap<String, String>();

		Enumeration<String> paramNames = request.getParameterNames();

		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();

			if (paramName.equals("sign")) {
				continue;
			}

			System.out.println(paramName);

			String paramValues = (String) request.getParameter(paramName);

			System.out.println(paramValues);

			paramsMap.put(paramName, paramValues);

		}

		StringBuilder sb = new StringBuilder();

		Set es = paramsMap.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("token=" + "token");
		System.out.println("服务端签名" + sb.toString());
		return null;
	}

}
