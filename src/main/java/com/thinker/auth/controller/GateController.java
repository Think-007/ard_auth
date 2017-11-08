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

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.think.creator.domain.ProcessResult;
import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.domain.UserRegistParam;
import com.thinker.auth.exception.TelNumberRepeatException;
import com.thinker.auth.exception.UserNameRepeatException;
import com.thinker.auth.service.UserRegistService;
import com.thinker.auth.util.ArdError;
import com.thinker.auth.util.ArdLog;
import com.thinker.auth.util.TokenUtil;

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
@Controller
@RequestMapping("/gate")
public class GateController {

	private static final Logger logger = LoggerFactory
			.getLogger(GateController.class);

	// 随机盐值
	@Value("${shiro.salt}")
	private String saltStr;
	// 加盐次数
	@Value("${salt.hashIterations}")
	private int hashIterations;

	@Resource
	private UserRegistService userRegistService;

	@RequestMapping("/registration")
	@ResponseBody
	public ProcessResult registUser(UserRegistParam userRegistParam) {
		ArdLog.info(logger, "enter registUser ", null, "userRegistParam: "
				+ userRegistParam);
		ProcessResult processResult = new ProcessResult();

		try {
			// 1.校验参数

			// 2.密码加盐

			ArdLog.debug(logger, "registUser", null, "salt: " + saltStr
					+ "hashIterations: " + hashIterations);
			System.out.println(saltStr);
			System.out.println(hashIterations);

			Md5Hash mh = new Md5Hash(userRegistParam.getPassword(), saltStr,
					hashIterations);
			System.out.println(mh.toString());

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

	/**
	 * 实际的登录代码 如果登录成功，跳转至首页；登录失败，则将失败信息反馈对用户
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/app_authentication")
	@ResponseBody
	public ProcessResult doLogin(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		ProcessResult result = new ProcessResult();
		result.setRetCode(ProcessResult.FAILED);

		try {
			String telnum = request.getParameter("telnum");
			String password = request.getParameter("password");
			System.out.println(telnum);
			System.out.println(password);
			// shiro认证登录
			login(result, telnum, password);

			// 4.生成token
			String loginToken = TokenUtil.generateToken(telnum);

		} catch (Throwable t) {
			result.setRetCode(ProcessResult.FAILED);
			result.setErrorCode(ArdError.EXCEPTION);
			result.setErrorDesc(ArdError.EXCEPTION_MSG);
			t.printStackTrace();
		}

		return result;
	}

	/**
	 * 登录
	 * 
	 * @param result
	 * @param userName
	 * @param password
	 */
	private void login(ProcessResult result, String telnum, String password) {
		String msg;
		UsernamePasswordToken token = new UsernamePasswordToken(telnum,
				password);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);

			if (subject.isAuthenticated()) {

				result.setRetCode(ProcessResult.SUCCESS);

			}

		} catch (IncorrectCredentialsException e) {
			msg = "登录密码错误. Password for account " + token.getPrincipal()
					+ " was incorrect.";
			result.setErrorCode(ArdError.PASSWORD_ERROR);
			result.setErrorDesc(msg);
			System.out.println(msg);
		} catch (DisabledAccountException e) {
			msg = "帐号已被注销. The account for username " + token.getPrincipal()
					+ " was disabled.";
			result.setErrorCode(ArdError.ACCOUNT_LOGOUT);
			result.setErrorDesc(msg);
			System.out.println(msg);
		} catch (UnknownAccountException e) {
			msg = "帐号不存在. There is no user with username of "
					+ token.getPrincipal();
			result.setErrorCode(ArdError.USER_NOT_EXIST);
			result.setErrorDesc(msg);
			System.out.println(msg);
		}
	}

	// /**
	// * web端登录地址
	// *
	// * @return
	// */
	// @RequestMapping("/web_authentication")
	// public ModelAndView webLogin(HttpServletRequest request,
	// HttpServletResponse response, Model model) {
	//
	// ModelAndView mv = new ModelAndView();
	// // 1、校验验证码
	//
	// // 2、校验用户信息
	//
	// try {
	// String msg = "";
	// String userName = request.getParameter("userName");
	// String password = request.getParameter("password");
	// System.out.println(userName);
	// System.out.println(password);
	// UsernamePasswordToken token = new UsernamePasswordToken(userName,
	// password);
	// token.setRememberMe(true);
	// Subject subject = SecurityUtils.getSubject();
	// try {
	// subject.login(token);
	// if (subject.isAuthenticated()) {
	//
	// mv.addObject("retcode", "0");
	// mv.setViewName("/admin/mainpage");
	// return mv;
	// }
	// mv.addObject("retcode", "-1");
	// mv.setViewName("/home");
	//
	// } catch (IncorrectCredentialsException e) {
	// msg = "登录密码错误. Password for account " + token.getPrincipal()
	// + " was incorrect.";
	// mv.addObject("errorcode", ArdError.PASSWORD_ERROR + "");
	// mv.addObject("errordesc", msg);
	// System.out.println(msg);
	// } catch (DisabledAccountException e) {
	// msg = "帐号已被注销. The account for username "
	// + token.getPrincipal() + " was disabled.";
	// mv.addObject("errorcode", ArdError.ACCOUNT_LOGOUT + "");
	// mv.addObject("errordesc", msg);
	// System.out.println(msg);
	// } catch (UnknownAccountException e) {
	// msg = "帐号不存在. There is no user with username of "
	// + token.getPrincipal();
	// mv.addObject("errorcode", ArdError.USER_NOT_EXIST + "");
	// mv.addObject("errordesc", msg);
	// System.out.println(msg);
	// }
	//
	// } catch (Throwable t) {
	// mv.addObject(ProcessResult.FAILED);
	// mv.addObject("errorcode", ArdError.EXCEPTION + "");
	// mv.addObject("errordesc", ArdError.EXCEPTION_MSG);
	// t.printStackTrace();
	// }
	// return mv;
	// }

	@RequestMapping("/signout_req/{uid}")
	public void checkOut(String uid) {

		SecurityUtils.getSubject().logout();
		// app还要删除token

	}

	@RequestMapping("/password_reset")
	public ProcessResult resetPassword() {

		ProcessResult result = new ProcessResult();

		return result;

	}

}
