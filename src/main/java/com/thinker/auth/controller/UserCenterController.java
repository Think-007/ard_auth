package com.thinker.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.think.creator.domain.ProcessResult;
import com.thinker.auth.util.Redis;

@RestController
@RequestMapping("/filter/auth")
public class UserCenterController {

	// @RequestMapping("/signin/{uid}/{timestamp}")
	// public ProcessResult singnIn() {
	//
	// return null;
	//
	// }
	/**
	 * 上传头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/headpic")
	public ProcessResult uploadPic(HttpServletRequest request,
			HttpServletResponse response) {

		return null;
	}

	/**
	 * 更新头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user_head/{uid}")
	public ProcessResult setHeadPic(HttpServletRequest request,
			HttpServletResponse response) {

		return null;
	}

	/**
	 * 校验旧密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/password/old/{uid}/{timestamp}", method = RequestMethod.PUT)
	public ProcessResult checkOldPassword(HttpServletRequest request,
			HttpServletResponse response, String oldPassword) {

		ProcessResult result = new ProcessResult();

		return result;

	}

	/**
	 * 更新新密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/password/new/{uid}/{timestamp}", method = RequestMethod.PUT)
	public ProcessResult changePassword(HttpServletRequest request,
			HttpServletResponse response, String newPassword) {

		ProcessResult result = new ProcessResult();

		return result;

	}

	/**
	 * 更改电话
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/telnumber/{uid}/{timestamp}", method = RequestMethod.PUT)
	public ProcessResult resetTelnumber(HttpServletRequest request,
			HttpServletResponse response) {

		ProcessResult result = new ProcessResult();

		return result;

	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @param uid
	 */
	@RequestMapping(value = "/out/{uid}/{timestamp}", method = RequestMethod.POST)
	public void checkOut(HttpServletRequest request,
			HttpServletResponse response, String uid) {

		String token = request.getHeader("token");
		// app还要删除token
		Redis.redis.remove(token);

	}

}
