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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAccount;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.ArdUserBm;
import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.domain.UserRegistParam;
import com.thinker.auth.domain.result.LoginResult;
import com.thinker.auth.exception.PassWordErrorException;
import com.thinker.auth.exception.QqRepeatException;
import com.thinker.auth.exception.SinaRepeatException;
import com.thinker.auth.exception.TelNumberRepeatException;
import com.thinker.auth.exception.UserLockException;
import com.thinker.auth.exception.UserNameRepeatException;
import com.thinker.auth.exception.UserNotExistException;
import com.thinker.auth.exception.WeiChatRepeatException;
import com.thinker.auth.service.AuthUserService;
import com.thinker.auth.service.UserAccountService;
import com.thinker.auth.service.UserInfoService;
import com.thinker.auth.service.UserRegistService;
import com.thinker.auth.util.Redis;
import com.thinker.auth.util.TokenUtil;
import com.thinker.creator.domain.ProcessResult;
import com.thinker.security.Base64;
import com.thinker.security.RSAEncrypt;
import com.thinker.util.ArdConst;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;
import com.thinker.util.ArdUserConst;

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
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

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

	// 账户业务
	@Resource
	private UserAccountService userAccountService;

	/**
	 * 用户注册接口 手机号_密码_昵称_设备号
	 * 
	 * @param registInfo
	 * @param smsCode
	 * @return
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ProcessResult registUser(String registInfo, String smsCode,
			String publicKey) {
		ArdLog.info(logger, "enter registUser ", null, "userRegistParam: "
				+ registInfo + "smsCode: " + smsCode);
		ProcessResult processResult = new ProcessResult();

		try {

			// 1.解析用户注册信息密文
			String[] userInfo = authUserService.decryptReqStr(registInfo);

			String telnum = userInfo[0];
			String password = userInfo[1];
			String userName = userInfo[2];
			String deviceInfo = userInfo[3];

			UserRegistParam userRegistParam = new UserRegistParam();
			userRegistParam.setTelNumber(telnum);
			userRegistParam.setPassword(password);
			userRegistParam.setUserName(userName);

			// 2.校验登录信息
			String redisSmsCode = (String) Redis.redis
					.get(ArdConst.PROJECT_FLAG + telnum + "_auth");

			if (smsCode == null || !smsCode.equals(redisSmsCode)) {

				processResult.setRetCode(ArdError.SMS_CODE_ERROR);
				return processResult;
			}

			// 3.密码加盐
			ArdLog.debug(logger, "registUser", null, "salt: " + saltStr
					+ "hashIterations: " + hashIterations);
			Md5Hash mh = new Md5Hash(userRegistParam.getPassword(), saltStr,
					hashIterations);
			System.out.println(mh.toString());
			userRegistParam.setPassword(mh.toString());

			// 4.创建用户,并绑定信息,默认roleid为0 ，是普通用户
			userRegistService.regitsUser(userRegistParam, saltStr,
					ArdUserConst.NORMAL_USER, ArdUserConst.PHONE);
			// 删除smscode
			Redis.redis.remove(ArdConst.PROJECT_FLAG + telnum + "_auth");

			// 5.登录
			login(publicKey, processResult, telnum, deviceInfo);

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
		} catch (UserNameRepeatException e) {

			processResult.setRetCode(ArdError.NAME_REPEAT);
			processResult.setRetMsg("昵称重复");
		} catch (TelNumberRepeatException e1) {

			processResult.setRetCode(ArdError.TEL_NUM_REGISTED);
			processResult.setRetMsg("号码已注册");

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

	private void login(String publicKey, ProcessResult processResult,
			String telnum, String deviceInfo) throws Exception {
		// 1.生成token和自动登录令牌
		String token = TokenUtil.generateToken();
		String loginToken = TokenUtil.generateLoginToken(deviceInfo + telnum);
		String retInfo = token + "_" + loginToken;
		// 将token用客户端给公钥加密
		String encryptToken = Base64.encode((RSAEncrypt.encrypt(
				RSAEncrypt.loadPublicKeyByStr(publicKey), retInfo.getBytes())));
		// 2、根据telNum或者thirdid查询用户id
		ArdUser ardUser = userInfoService.getUserInfoByTelNumber(telnum);
		ardUser.setPassword(null);
		ardUser.setSalt(null);
		ardUser.setHasPassword(ArdUserConst.HAS_PWD);

		// 3、根据uid查询别名
		ArdUserBm ardUserBm = userInfoService
				.getUserBmById(ardUser.getUserId());

		// 4、根据uid查询角色信息

		ArdUserRole ardUserRole = userInfoService.getArdUserRoleById(ardUser
				.getUserId());

		// 5、根据uid查询attach列表
		List<ArdUserAttach> attachList = userInfoService
				.getAttachListByUid(ardUser.getUserId());
		for (ArdUserAttach temp : attachList) {
			if (temp.getMainAttach() == ArdConst.MAIN_ATTACH) {
				ardUser.setMainAttach(temp);
			}
		}

		// 6、查询账户列表
		List<ArdUserAccount> accountList = userAccountService
				.getUserAccountList(ardUser.getUserId());

		// 7、判断今日是否签到
		int isSigned = 0;
		for (ArdUserAccount temp : accountList) {
			if (temp.getAccountType() == ArdConst.BONUS) {
				Date today = Calendar.getInstance().getTime();
				if (sdf.format(temp.getUpdateTime()).equals(sdf.format(today))) {
					isSigned = 1;
				}
				break;
			}
		}

		LoginResult loginResult = new LoginResult();
		loginResult.setToken(encryptToken);
		loginResult.setArdUser(ardUser);
		loginResult.setArdUserBm(ardUserBm);
		loginResult.setArdUserRole(ardUserRole);
		loginResult.setBindList(attachList);
		loginResult.setAccountList(accountList);
		loginResult.setIsSigned(isSigned);

		processResult.setRetObj(loginResult);

		// 3、存储token和用户信息

		Redis.redis.put(ArdConst.PROJECT_FLAG + token, loginResult);

		Redis.redis.put(ArdConst.PROJECT_FLAG + loginToken, ardUser);
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
	public ProcessResult doLogin(String loginInfo, String publicKey) {

		ArdLog.info(logger, "enter doLogin ", null, "loginInfo : " + loginInfo
				+ " publicKey : " + publicKey);

		ProcessResult processResult = new ProcessResult();

		try {
			// 1.解析用户登录信息密文
			String[] userInfo = authUserService.decryptReqStr(loginInfo);

			String telnum = userInfo[0];
			String password = userInfo[1];
			String deviceInfo = userInfo[2];

			ArdLog.debug(logger, "doLogin", null, " telnum : " + telnum
					+ " password : " + password + " deviceInfo : " + deviceInfo);

			// 2、用户认证
			String msg = null;

			try {

				if (authUserService.authUser(telnum, password)) {

					processResult.setRetCode(ProcessResult.SUCCESS);
					processResult.setRetMsg("ok");
					// 3.登录
					login(publicKey, processResult, telnum, deviceInfo);
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

		ArdLog.info(logger, "finish dologin", null, processResult);
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
	public ProcessResult autoLogin(String autoLoginInfo, String publicKey) {

		ArdLog.info(logger, " enter autoLogin", null, " autoLoginInfo : "

		+ autoLoginInfo);

		ProcessResult processResult = new ProcessResult();

		try {
			// 解析请求参数
			String[] reqInfo = authUserService.decryptReqStr(autoLoginInfo);
			String loginToken = reqInfo[0];
			// 1、根据token获取用户信息
			ArdUser ardUser = (ArdUser) Redis.redis.get(ArdConst.PROJECT_FLAG
					+ loginToken);

			// 保持会话长连接的token,类似session
			String token = TokenUtil.generateToken();

			String retInfo = token + "_" + loginToken;

			// 2、将token用客户端给公钥加密返回给客户端
			String encryptToken = new String(RSAEncrypt.encrypt(
					RSAEncrypt.loadPublicKeyByStr(publicKey),
					retInfo.getBytes()));

			if (ardUser == null) {
				processResult.setRetCode(ArdError.AUTO_LOGIN_TOKEN_TIME_OUT);
				processResult.setRetMsg("令牌过期");

				return processResult;
			}
			// 3、根据uid查询别名
			ArdUserBm ardUserBm = userInfoService.getUserBmById(ardUser
					.getUserId());

			// 4、根据uid查询角色信息

			ArdUserRole ardUserRole = userInfoService
					.getArdUserRoleById(ardUser.getUserId());

			// 5、根据uid查询attach列表
			List<ArdUserAttach> attachList = userInfoService
					.getAttachListByUid(ardUser.getUserId());

			// 6、查询账户列表
			List<ArdUserAccount> accountList = userAccountService
					.getUserAccountList(ardUser.getUserId());
			LoginResult loginResult = new LoginResult();
			loginResult.setToken(encryptToken);
			loginResult.setArdUser(ardUser);
			loginResult.setArdUserRole(ardUserRole);
			loginResult.setArdUserBm(ardUserBm);
			loginResult.setAccountList(accountList);
			loginResult.setBindList(attachList);

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setRetObj(loginResult);
		} catch (Throwable t) {
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			t.printStackTrace();
			ArdLog.error(logger, "app auto login error", null, processResult, t);
		}
		ArdLog.info(logger, " finish autoLogin", null, processResult);
		return processResult;
	}

	/**
	 * 第三方登录接口 第三方id_用户名_设备号_头像地址
	 * 
	 * @param thirdInfo
	 *            加密后的登录信息
	 * @param key
	 *            客户端公钥
	 * @param type
	 *            登录类型 (微博、微信、qq)
	 * @return
	 */
	@RequestMapping("/third_auth")
	public ProcessResult thirdLogin(String thirdInfo, String publicKey,
			int type, String headUrl) {

		ArdLog.info(logger, "enter thirdLogin ", null, " thirdinfo : "
				+ thirdInfo + " publickey : " + publicKey);

		ProcessResult processResult = new ProcessResult();
		try {
			// 1.获取注册信息.
			String[] userInfo = authUserService.decryptReqStr(thirdInfo);
			String thirdId = userInfo[0];
			String userName = userInfo[1];
			String deviceInfo = userInfo[2];
			// 2.查看是否存在,如果不存在就注册
			// UserInfoDetail userInfoDetail = userInfoService
			// .getUserInfoDetailByTelNumber(thirdId);
			ArdUserAttach ardUserAttach = userInfoService
					.getAttachListByTelNumber(thirdId);
			if (ardUserAttach == null) {
				UserRegistParam userRegistParam = new UserRegistParam();
				userRegistParam.setTelNumber(thirdId);
				userRegistParam.setUserName(userName);
				userRegistParam.setHeadPicUrl(headUrl);
				userRegistParam.setPassword("123456");
				// 3.密码加盐
				ArdLog.debug(logger, "third registUser", null, "salt: "
						+ saltStr + "hashIterations: " + hashIterations);
				Md5Hash mh = new Md5Hash(userRegistParam.getPassword(),
						saltStr, hashIterations);
				System.out.println(mh.toString());
				userRegistParam.setPassword(mh.toString());
				// 4.注册用户
				userRegistService.regitsUser(userRegistParam, saltStr,
						ArdUserConst.NORMAL_USER, type);
			}
			// 5.登录
			login(publicKey, processResult, thirdId, deviceInfo);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");

		} catch (UserNameRepeatException e) {
			processResult.setRetCode(ArdError.NAME_REPEAT);
			processResult.setRetMsg("昵称重复");
		} catch (TelNumberRepeatException e1) {
			processResult.setRetCode(ArdError.TEL_NUM_REGISTED);
			processResult.setRetMsg("号码已注册");
		} catch (WeiChatRepeatException e2) {
			processResult.setRetCode(ArdError.WEI_CHAT_REGISTED);
			processResult.setRetMsg("微信已经被绑定");
		} catch (QqRepeatException e3) {
			processResult.setRetCode(ArdError.QQ_REGISTED);
			processResult.setRetMsg("QQ已经被绑定");
		} catch (SinaRepeatException e3) {
			processResult.setRetCode(ArdError.SINA_REGISTED);
			processResult.setRetMsg("微博已经被绑定");
		} catch (Throwable t) {
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "registUser error", null, null, t);
			t.printStackTrace();
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

			String code = (String) Redis.redis.get(ArdConst.PROJECT_FLAG
					+ telNumber + "_auth");
			if (code != null && code.equals(smsCode)) {
				// 更改密码
				ArdUser ardUser = userInfoService
						.getUserInfoByTelNumber(telNumber);
				userInfoService.updateUserPassword(ardUser.getUserId(),
						newPassowrd);
				// 删除smscode
				Redis.redis.remove(ArdConst.PROJECT_FLAG + telNumber + "_auth");
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

	// @RequestMapping("/testparam")
	// public ProcessResult testParam(HttpServletRequest request,
	// HttpServletResponse response, String telNumber, String a) {
	//
	// SortedMap<String, String> paramsMap = new TreeMap<String, String>();
	//
	// Enumeration<String> paramNames = request.getParameterNames();
	//
	// while (paramNames.hasMoreElements()) {
	// String paramName = paramNames.nextElement();
	//
	// if (paramName.equals("sign")) {
	// continue;
	// }
	//
	// System.out.println(paramName);
	//
	// String paramValues = (String) request.getParameter(paramName);
	//
	// System.out.println(paramValues);
	//
	// paramsMap.put(paramName, paramValues);
	//
	// }
	//
	// StringBuilder sb = new StringBuilder();
	//
	// Set es = paramsMap.entrySet();
	// Iterator it = es.iterator();
	// while (it.hasNext()) {
	// Map.Entry entry = (Map.Entry) it.next();
	// String k = (String) entry.getKey();
	// String v = (String) entry.getValue();
	// if (null != v && !"".equals(v)) {
	// sb.append(k + "=" + v + "&");
	// }
	// }
	// sb.append("token=" + "token");
	// System.out.println("服务端签名" + sb.toString());
	// return null;
	// }

}
