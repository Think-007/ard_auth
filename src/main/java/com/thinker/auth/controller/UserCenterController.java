package com.thinker.auth.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.think.creator.domain.ProcessResult;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.service.AuthUserService;
import com.thinker.auth.service.UserInfoService;
import com.thinker.auth.util.Redis;
import com.thinker.util.ArdError;

@RestController
@RequestMapping("/filter/auth")
public class UserCenterController {

	@Resource
	private UserInfoService userInfoService;
	// 用户登录鉴权业务
	@Resource
	private AuthUserService authUserService;

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

		String filePath = "D:/upload/users/imgs/";
		// 获取图片原始名称
		String originalFilename = file.getOriginalFilename();
		// 图片扩展名
		String types = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

		String uid = (String) request.getAttribute("uid");

		try {
			System.out.println(types);
			// 以用户id加图片扩展名给图片命名
			String newFileName = uid + "." + types;
			File picfile = new File(filePath + "uid/" + newFileName);
			File micPicFile = new File(filePath + "uid/mic/" + newFileName);
			// 上传
			// 以80*80大小改变图片，此处使用thumbnailator-0.4.2.jar改变图片大小
			Thumbnails.of(picfile).scale(1f).outputQuality(0.25f).toFile(picfile);

			Thumbnails.of(picfile).size(80, 80).keepAspectRatio(false).toFile(micPicFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 更新头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user_head")
	public ProcessResult setHeadPic(HttpServletRequest request, HttpServletResponse response, String picPath,
			String micPicPath) {
		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(ProcessResult.FAILED);
		processResult.setRetMsg("failed");

		try {
			String userId = (String) request.getAttribute("uid");
			ArdUserAttach ardUserAttach = new ArdUserAttach();
			ardUserAttach.setUserId(userId);
			ardUserAttach.setThumbURL(micPicPath);
			ardUserAttach.setHeadpicURL(picPath);
			userInfoService.updateUserHeadPic(ardUserAttach);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");

		} catch (Exception e) {

			processResult.setErrorCode(ArdError.EXCEPTION);
			processResult.setErrorDesc(ArdError.EXCEPTION_MSG);
			e.printStackTrace();
		}

		return processResult;
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

		try {
			String[] reqInfo = authUserService.decryptReqStr(oldPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		try {
			String[] reqInfo = authUserService.decryptReqStr(newPassword);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

		String uid = request.getHeader("uid");
		// app还要删除token
		Redis.redis.remove(uid);

	}

}
