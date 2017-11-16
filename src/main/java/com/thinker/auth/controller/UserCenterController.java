package com.thinker.auth.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.think.creator.domain.ProcessResult;
import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.service.AuthUserService;
import com.thinker.auth.service.UserInfoService;
import com.thinker.auth.util.Redis;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;

@RestController
@RequestMapping("/filter/auth")
public class UserCenterController {

	private static final Logger logger = LoggerFactory
			.getLogger(UserCenterController.class);

	// 随机盐值
	@Value("${shiro.salt}")
	private String saltStr;
	// 加盐次数
	@Value("${salt.hashIterations}")
	private int hashIterations;

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
	public ProcessResult uploadPic(@RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(ProcessResult.FAILED);
		processResult.setRetMsg("failed");

		String uid = Math.random() * 1000 + "";

		// 获取图片原始名称
		String originalFilename = file.getOriginalFilename();
		// 图片扩展名
		String types = originalFilename.substring(
				originalFilename.lastIndexOf(".") + 1).toLowerCase();
		String newFileName = uid + "." + types;
		String filePath = "D:/upload/users/imgs/" + uid + "/";

		BufferedOutputStream out = null;
		try {

			File fileDir = new File(filePath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

			// 以用户id加图片扩展名给图片命名
			File originfile = new File(filePath + "origin_" + originalFilename);
			File picfile = new File(filePath + "big_" + newFileName);
			File micPicFile = new File(filePath + "mic_" + newFileName);

			System.out.println(originfile.toString());
			if (!originfile.exists()) {
				originfile.createNewFile();
			}
			if (!picfile.exists()) {
				picfile.createNewFile();
			}
			if (!micPicFile.exists()) {
				micPicFile.createNewFile();
			}
			// 保存原始图片、压缩图片和微缩图片
			out = new BufferedOutputStream(new FileOutputStream(originfile));
			out.write(file.getBytes());
			out.flush();
			out.close();
			Thumbnails.of(originfile).scale(1f).outputQuality(0.25f)
					.toFile(picfile);
			Thumbnails.of(originfile).size(80, 80).keepAspectRatio(false)
					.toFile(micPicFile);

		} catch (Exception e) {
			processResult.setErrorCode(ArdError.EXCEPTION);
			processResult.setErrorDesc(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "uploadPic", null, null, e);
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				ArdLog.error(logger, "uploadPic", null, null, e);
				e.printStackTrace();
			}
		}

		return processResult;
	}

	/**
	 * 更新头像
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/user_head")
	public ProcessResult setHeadPic(HttpServletRequest request,
			HttpServletResponse response, String picPath, String micPicPath) {
		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(ProcessResult.FAILED);
		processResult.setRetMsg("failed");

		try {
			String userId = (String) request.getAttribute("uid");
			ArdUserAttach ardUserAttach = new ArdUserAttach();
			ardUserAttach.setUserId(userId);
			ardUserAttach.setThumbURL(micPicPath);
			ardUserAttach.setHeadpicURL(picPath);
			ArdLog.info(logger, "setHeadPic", null, ardUserAttach);
			userInfoService.updateUserHeadPic(ardUserAttach);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");

		} catch (Exception e) {

			processResult.setErrorCode(ArdError.EXCEPTION);
			processResult.setErrorDesc(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "user_head", null, null, e);
			e.printStackTrace();
		}

		ArdLog.info(logger, "setHeadPic", null, processResult);
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
	public ProcessResult checkOldPassword(HttpServletRequest request,
			HttpServletResponse response, String oldPassword) {

		ArdLog.info(logger, "checkOldPassword", null, "oldPassword : "
				+ oldPassword);
		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(ProcessResult.FAILED);
		processResult.setRetMsg("failed");
		try {
			// 解析用户 就密码
			String userId = (String) request.getAttribute("uid");
			String[] reqInfo = authUserService.decryptReqStr(oldPassword);
			System.out.println(reqInfo.length + reqInfo[0]);

			// 1.根据uid查询用户信息
			ArdUser ardUser = userInfoService.getUserInfoByuserId(userId);
			if (ardUser != null) {

				Md5Hash mh = new Md5Hash(reqInfo[0], ardUser.getSalt(),
						hashIterations);
				// 2.判断就密码是否正确
				if (mh.toString().equals(ardUser.getPassword())) {
					processResult.setRetCode(ProcessResult.SUCCESS);
					processResult.setRetMsg("ok");
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			processResult.setErrorCode(ArdError.EXCEPTION);
			processResult.setErrorDesc(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "checkOldPassword", null, null, e);
			e.printStackTrace();
		}
		ArdLog.info(logger, "checkOldPassword", null, processResult);
		return processResult;

	}

	/**
	 * 更新新密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/password/new", method = RequestMethod.PUT)
	public ProcessResult changePassword(HttpServletRequest request,
			HttpServletResponse response, String newPassword) {

		ArdLog.info(logger, "enter changePassword", null, newPassword);
		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(ProcessResult.FAILED);
		processResult.setRetMsg("failed");

		try {
			// 解析用户参数
			String userId = (String) request.getHeader("uid");
			String[] reqInfo = authUserService.decryptReqStr(newPassword);

			System.out.println(reqInfo.length + reqInfo[0]);
			Md5Hash mh = new Md5Hash(reqInfo[0], saltStr, hashIterations);
			ArdUser ardUser = new ArdUser();
			ardUser.setUserId(userId);
			ardUser.setPassword(mh.toString());

			// 跟新密码为新的密码
			userInfoService.updateUserInfo(ardUser);

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");

		} catch (Exception e) {

			processResult.setErrorCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "changePassword", null, null, e);
			e.printStackTrace();
		}

		ArdLog.info(logger, "changePassword", null, processResult);

		return processResult;

	}

	/**
	 * 更改电话
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/telnumber", method = RequestMethod.PUT)
	public ProcessResult resetTelnumber(HttpServletRequest request,
			HttpServletResponse response, String telNumber, String smsCode) {

		ProcessResult result = new ProcessResult();

		return result;

	}

	@RequestMapping(value = "/username", method = RequestMethod.POST)
	public ProcessResult modifyUserName(HttpServletRequest request,
			HttpServletResponse response, String userName) {

		String userId = request.getHeader("uid");

		try {
			userInfoService.updateUserBm(userId, userName);
		}

		catch (Throwable t) {
			// TODO Auto-generated catch block
			t.printStackTrace();
		}

		return null;
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @param uid
	 */
	@RequestMapping(value = "/out", method = RequestMethod.POST)
	public void checkOut(HttpServletRequest request,
			HttpServletResponse response) {

		String uid = request.getHeader("uid");
		// app还要删除token
		Redis.redis.remove(uid);

	}

	/**
	 * 解绑手机号码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/unbind")
	public ProcessResult unbindTelNumber(HttpServletRequest request,
			HttpServletResponse response, String telNumber) {

		ProcessResult result = new ProcessResult();

		return result;
	}

}
