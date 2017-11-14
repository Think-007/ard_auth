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
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.think.creator.domain.ProcessResult;
import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.domain.UserInfoDetail;
import com.thinker.auth.domain.UserRegistParam;
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
import com.thinker.security.RSAEncrypt;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;
import com.thinker.util.JsonUtils;

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

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ProcessResult registUser(String registInfo, String smsCode) {
		ArdLog.info(logger, "enter registUser ", null, "userRegistParam: "
				+ registInfo + "smsCode: " + smsCode);
		ProcessResult processResult = new ProcessResult();

		try {

			// 解析用户登录信息密文
			String[] userInfo = decryptReqStr(registInfo);

			String telnum = userInfo[0];
			String password = userInfo[1];
			String userName = userInfo[3];

			UserRegistParam userRegistParam = new UserRegistParam();
			userRegistParam.setTelNumber(telnum);
			userRegistParam.setPassword(password);
			userRegistParam.setUserName(userName);

			// 1.校验参数

			// 2.密码加盐

			ArdLog.debug(logger, "registUser", null, "salt: " + saltStr
					+ "hashIterations: " + hashIterations);
			System.out.println(saltStr);
			System.out.println(hashIterations);

			Md5Hash mh = new Md5Hash(userRegistParam.getPassword(), saltStr,
					hashIterations);
			System.out.println(mh.toString());
			userRegistParam.setPassword(mh.toString());

			// 3.创建用户,并绑定信息,默认roleid为0 ，是普通用户

			ArdUserRole ardUserRole = new ArdUserRole();

			userRegistService.regitsUser(userRegistParam, saltStr, ardUserRole);

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
		} catch (UserNameRepeatException e) {

			processResult.setRetCode(ProcessResult.FAILED);
			processResult.setRetMsg("failed");
			processResult.setErrorCode(ArdError.NAME_REPEAT);
			ArdLog.error(logger, "registUser error username used", null, null,
					e);
			e.printStackTrace();
		} catch (TelNumberRepeatException e1) {

			processResult.setRetCode(ProcessResult.FAILED);
			processResult.setRetMsg("failed");
			processResult.setErrorCode(ArdError.TEL_NUM_REGISTED);
			ArdLog.error(logger, "registUser error telnum used", null, null, e1);
			e1.printStackTrace();

		} catch (Throwable t) {
			processResult.setRetCode(ProcessResult.FAILED);
			processResult.setErrorCode(ArdError.EXCEPTION);
			processResult.setErrorDesc(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "registUser error", null, null, t);
			t.printStackTrace();
		}
		ArdLog.info(logger, "finish registUser ", null, "processresult: "
				+ processResult);

		return processResult;
	}

	private String[] decryptReqStr(String encryptStr) throws Exception {
		String privateKey = AuthCodeController.keyCache.get("privateKey");
		String userInfoStr = new String(RSAEncrypt.decrypt(
				RSAEncrypt.loadPrivateKeyByStr(privateKey),
				encryptStr.getBytes()));

		String[] userInfo = userInfoStr.split("_");
		return userInfo;
	}

	/**
	 * 实际的登录代码 如果登录成功，跳转至首页；登录失败，则将失败信息反馈对用户
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/app_authentication", method = RequestMethod.POST)
	public ProcessResult doLogin(String loginInfo, String key) {

		ProcessResult result = new ProcessResult();
		result.setRetCode(ProcessResult.FAILED);
		result.setRetMsg("failed");

		try {
			// 解析用户登录信息密文
			String[] userInfo = decryptReqStr(loginInfo);
			// 客户端公钥
			String publicKey = key;

			String telnum = userInfo[0];
			String password = userInfo[1];

			System.out.println(telnum);
			System.out.println(password);

			// 1、用户认证
			String msg;

			try {

				if (authUserService.authUser(telnum, password)) {

					result.setRetCode(ProcessResult.SUCCESS);
					result.setRetMsg("ok");
					String loginToken = TokenUtil.generateToken(telnum);
					// 将token用客户端给公钥加密返回给客户端
					String encryptToken = new String(RSAEncrypt.encrypt(
							RSAEncrypt.loadPublicKeyByStr(publicKey),
							loginToken.getBytes()));
					// 2、返回用户信息
					UserInfoDetail userInfoDetail = userInfoService
							.getUserInfoDetailByTelNumber(telnum);
					Map<Object, Object> map = new HashMap<Object, Object>();
					map.put("userInof", userInfoDetail);
					map.put("tokenInfo", encryptToken);
					result.setRetObj(map);

					// 3、存储token和用户信息

					Redis.redis.put(loginToken,
							JsonUtils.toJson(userInfoDetail));

				}

			} catch (PassWordErrorException e) {
				msg = "登录密码错误. Password for account " + telnum
						+ " was incorrect.";
				result.setErrorCode(ArdError.PASSWORD_ERROR);
				result.setErrorDesc(msg);
				System.out.println(msg);
			} catch (UserLockException e) {
				msg = "帐号已被锁定. The account for username " + telnum
						+ " was disabled.";
				result.setErrorCode(ArdError.USER_LOCKED);
				result.setErrorDesc(msg);
				System.out.println(msg);
			} catch (UserNotExistException e) {
				msg = "帐号不存在. There is no user with username of " + telnum;
				result.setErrorCode(ArdError.USER_NOT_EXIST);
				result.setErrorDesc(msg);
				System.out.println(msg);
			}

		} catch (Throwable t) {
			result.setRetCode(ProcessResult.FAILED);
			result.setErrorCode(ArdError.EXCEPTION);
			result.setErrorDesc(ArdError.EXCEPTION_MSG);
			t.printStackTrace();
		}

		return result;
	}

	/**
	 * 忘记密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/password/{uid}/{timestamp}", method = RequestMethod.PUT)
	public ProcessResult resetPassword(HttpServletRequest request,
			HttpServletResponse response) {

		ProcessResult result = new ProcessResult();

		return result;

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

		return result;

	}

}
