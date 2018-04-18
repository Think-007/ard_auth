package com.thinker.auth.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAccount;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.ArdUserBm;
import com.thinker.auth.domain.UserRegistParam;
import com.thinker.auth.exception.UserNameRepeatException;
import com.thinker.auth.service.AuthUserService;
import com.thinker.auth.service.UserAccountService;
import com.thinker.auth.service.UserInfoService;
import com.thinker.auth.service.UserRegistService;
import com.thinker.auth.util.Redis;
import com.thinker.creator.domain.ProcessResult;
import com.thinker.util.ArdConst;
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

	// 用户账户业务
	@Resource
	private UserAccountService userAccountService;

	// 绑定用户信息业务
	@Resource
	private UserRegistService userRegistService;

	// 签到积分
	@Value("${ard.bonus}")
	private double bonus;

	/**
	 * 签到
	 * 
	 * @return
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ProcessResult singnIn(HttpServletRequest request,
			HttpServletResponse response) {
		ArdLog.debug(logger, "enter singnIn", null, null);

		ProcessResult processResult = new ProcessResult();

		try {
			String userId = request.getHeader("uid");

			System.out.println("--------------->" + userId);

			ArdUserAccount ardUserAccount = userAccountService
					.updateUseAccountInfoByUseId(userId, bonus);

			if (ardUserAccount != null) {

				processResult.setRetCode(ProcessResult.SUCCESS);
				processResult.setRetMsg("ok");
				processResult.setRetObj(ardUserAccount);
			} else {
				processResult.setRetCode(ArdError.SIGNED_OVER);
				processResult.setRetMsg("signed over");
			}
		} catch (Throwable t) {
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "signin", null, null, t);
			t.printStackTrace();
		}

		ArdLog.debug(logger, "finish singnIn", null, processResult);
		return processResult;

	}

	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public ProcessResult userSetting(HttpServletRequest request,
			HttpServletResponse response) {
		ArdLog.debug(logger, "enter settings", null, null);

		ProcessResult processResult = new ProcessResult();

		String userId = request.getHeader("uid");

		userAccountService.updateUseAccountInfoByUseId(userId, bonus);

		processResult.setRetCode(ProcessResult.SUCCESS);

		ArdLog.debug(logger, "finish settings", null, processResult);
		return processResult;

	}

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

			String[] picArr = new String[3];
			picArr[0] = originalFilename.toString();
			picArr[1] = picfile.toString();
			picArr[2] = micPicFile.toString();
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setRetObj(picArr);

		} catch (Exception e) {
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
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
	@RequestMapping(value = "/user_head", method = RequestMethod.POST)
	public ProcessResult setHeadPic(HttpServletRequest request,
			HttpServletResponse response, String picPath, String micPicPath) {
		ProcessResult processResult = new ProcessResult();

		try {
			String userId = (String) request.getAttribute("uid");

			ArdLog.info(logger, "setHeadPic", null, " userid: " + userId
					+ " picPaht : " + picPath + " micPicPaht : " + micPicPath);

			ArdUserAttach ardUserAttach = userInfoService.updateUserHeadPic(
					userId, picPath, micPicPath);

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setRetObj(ardUserAttach);

		} catch (Exception e) {

			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "user_head", null, null, e);
			e.printStackTrace();
		}

		ArdLog.info(logger, "setHeadPic", null, processResult);
		return processResult;
	}

	/**
	 * 校验旧密码 加密旧密码字段
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "/password/old", method = RequestMethod.POST)
	public ProcessResult checkOldPassword(HttpServletRequest request,
			HttpServletResponse response, String oldPassword) {

		ArdLog.info(logger, "checkOldPassword", null, "oldPassword : "
				+ oldPassword);
		ProcessResult processResult = new ProcessResult();
		try {
			// 解析用户 旧密码
			String userId = (String) request.getHeader("uid");
			String[] reqInfo = authUserService.decryptReqStr(oldPassword);
			System.out.println(reqInfo.length + reqInfo[0]);

			// 1.根据uid查询用户信息
			ArdUser ardUser = userInfoService.getUserInfoByuserId(userId);
			System.out.println(ardUser);
			if (ardUser != null) {

				Md5Hash mh = new Md5Hash(reqInfo[0], ardUser.getSalt(),
						hashIterations);
				System.out.println(mh.toString());

				String userPwd = mh.toString();
				// 2.判断就密码是否正确
				if (userPwd.equals(ardUser.getPassword())) {
					processResult.setRetCode(ProcessResult.SUCCESS);
					processResult.setRetMsg("ok");
				} else {
					processResult.setRetCode(ArdError.PASSWORD_ERROR);
					processResult.setRetMsg("pwd error");
				}

			}

		} catch (Exception e) {

			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			ArdLog.error(logger, "checkOldPassword", null, null, e);
			e.printStackTrace();
		}
		ArdLog.info(logger, "checkOldPassword", null, processResult);
		return processResult;

	}

	/**
	 * 更新新密码 加密新密码字段
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

		try {
			// 解析用户参数
			String userId = (String) request.getHeader("uid");
			String[] reqInfo = authUserService.decryptReqStr(newPassword);

			System.out.println(reqInfo.length + reqInfo[0]);
			Md5Hash mh = new Md5Hash(reqInfo[0], saltStr, hashIterations);

			// 跟新密码为新的密码
			userInfoService.updateUserPassword(userId, mh.toString());

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");

		} catch (Exception e) {

			processResult.setRetCode(ArdError.EXCEPTION);
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

		ArdLog.debug(logger, "enter resetTelnumber", null, " telNumber : "
				+ telNumber + " smsCode : " + smsCode);
		ProcessResult processResult = new ProcessResult();

		String userId = request.getHeader("uid");

		// 判断短信码是否正确啊
		if (smsCode == null
				|| smsCode.equals(Redis.redis.get(ArdConst.PROJECT_FLAG
						+ telNumber + "_auth"))) {

			processResult.setRetCode(ArdError.PARAM_ILLEGAL);

			return processResult;

		}

		// 更新电话
		ArdUserAttach ardUserAttach = userInfoService.updateTemNumberByUserId(
				userId, telNumber);

		processResult.setRetObj(ardUserAttach);

		ArdLog.debug(logger, "finishi resetTelnumber", null,
				" processresult : " + processResult);

		return processResult;

	}

	/**
	 * 更改昵称
	 * 
	 * @param request
	 * @param response
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/username", method = RequestMethod.PUT)
	public ProcessResult modifyUserName(HttpServletRequest request,
			HttpServletResponse response, String userName) {

		ArdLog.debug(logger, "enter modifyUserName", null, " userName : "
				+ userName);
		ProcessResult processResult = new ProcessResult();
		String userId = request.getHeader("uid");

		try {
			ArdUserBm ardUserBm = userInfoService
					.updateUserBm(userId, userName);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setRetObj(ardUserBm);

		} catch (UserNameRepeatException e) {

			processResult.setRetCode(ArdError.NAME_REPEAT);
			processResult.setRetMsg("昵称重复");

		} catch (Throwable t) {
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			t.printStackTrace();
		}

		ArdLog.debug(logger, "finish modifyUserName", null, processResult);
		return processResult;
	}

	/**
	 * 绑定第三方信息 加密thirdId_用户名
	 * 
	 * @return
	 */
	@RequestMapping(value = "/bindinfo", method = RequestMethod.POST)
	public ProcessResult bindThirdInfo(HttpServletRequest request,
			String thirdInfo, int bindType, String headUrl) {
		ArdLog.debug(logger, "enter bindThirdInfo", null, " thirdInfo : "
				+ thirdInfo + " bindType : " + bindType);
		ProcessResult processResult = new ProcessResult();
		String userId = request.getHeader("uid");

		try {
			// 1.获取第三方信息.
			String[] userInfo = authUserService.decryptReqStr(thirdInfo);
			String thirdId = userInfo[0];
			String userName = userInfo[1];

			// 2.构造参数，绑定信息
			UserRegistParam userRegistParam = new UserRegistParam();
			userRegistParam.setTelNumber(thirdId);
			userRegistParam.setUserName(userName);
			userRegistParam.setHeadPicUrl(headUrl);
			processResult = userRegistService.bindThirdInfo(userRegistParam,
					bindType, userId, ArdConst.NORMAL_ATTACH, Calendar
							.getInstance().getTime());
		} catch (Throwable t) {
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			t.printStackTrace();
		}

		ArdLog.debug(logger, "finish bindThirdInfo", null, "processresult : "
				+ processResult);

		return processResult;
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @param uid
	 */
	@RequestMapping(value = "/out", method = RequestMethod.GET)
	public void checkOut(HttpServletRequest request,
			HttpServletResponse response) {

		String token = request.getHeader("token");
		// app还要删除token
		Redis.redis.remove(ArdConst.PROJECT_FLAG + token);

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
			HttpServletResponse response, String thirdId, int type) {

		ProcessResult result = new ProcessResult();
		// 1、删除绑定信息后，还要确定主账号
		result.setRetCode(ProcessResult.SUCCESS);
		return result;
	}

	/**
	 * 意见反馈
	 * 
	 * @param request
	 * @param response
	 * @param feedBackInfo
	 * @return
	 */

	@RequestMapping("/feedBack")
	public ProcessResult feedBack(HttpServletRequest request,
			HttpServletResponse response, String feedBackInfo) {

		ProcessResult result = new ProcessResult();

		result.setRetCode(ProcessResult.SUCCESS);

		return result;
	}

}
