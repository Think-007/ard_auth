package com.thinker.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	@RequestMapping(value = "/headpic", method = RequestMethod.POST)
	public ProcessResult uploadPic(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

	/**
	 * 更新头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user_head/")
	public ProcessResult setHeadPic(String picPath, String micPicPath) {

		return null;
	}

	/**
	 * 校验旧密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/password/old", method = RequestMethod.GET)
	public ProcessResult checkOldPassword(HttpServletRequest request, HttpServletResponse response,
			String oldPassword) {

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
	@RequestMapping(value = "/password/new", method = RequestMethod.PUT)
	public ProcessResult changePassword(HttpServletRequest request, HttpServletResponse response, String newPassword) {

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
	@RequestMapping(value = "/telnumber", method = RequestMethod.PUT)
	public ProcessResult resetTelnumber(HttpServletRequest request, HttpServletResponse response) {

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
	@RequestMapping(value = "/out", method = RequestMethod.POST)
	public void checkOut(HttpServletRequest request, HttpServletResponse response) {

		String token = request.getHeader("token");
		// app还要删除token
		Redis.redis.remove(token);

	}

}
