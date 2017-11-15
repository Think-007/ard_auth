package com.thinker.auth.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	private static final Logger logger = LoggerFactory.getLogger(UserCenterController.class);

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
			ArdLog.info(logger, "setHeadPic", null, ardUserAttach);
			userInfoService.updateUserHeadPic(ardUserAttach);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");

		} catch (Exception e) {

			processResult.setErrorCode(ArdError.EXCEPTION);
			processResult.setErrorDesc(ArdError.EXCEPTION_MSG);
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
	public ProcessResult checkOldPassword(HttpServletRequest request, HttpServletResponse response,
			String oldPassword) {

		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(ProcessResult.FAILED);
		processResult.setRetMsg("failed");
		try {
			String[] reqInfo = authUserService.decryptReqStr(oldPassword);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	public ProcessResult changePassword(HttpServletRequest request, HttpServletResponse response, String newPassword) {

		ArdLog.info(logger, "enter changePassword", null, newPassword);
		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(ProcessResult.FAILED);
		processResult.setRetMsg("failed");

		try {
			String userId = (String) request.getAttribute("uid");
			String[] reqInfo = authUserService.decryptReqStr(newPassword);

			System.out.println(reqInfo.length + reqInfo[0]);
			Md5Hash mh = new Md5Hash(reqInfo[0], saltStr, hashIterations);
			ArdUser ardUser = new ArdUser();
			ardUser.setUserId(userId);
			ardUser.setPassword(mh.toString());
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

	/**
	 * 解绑手机号码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/unbind")
	public ProcessResult unbindTelNumber(HttpServletRequest request, HttpServletResponse response,
			String telNumber) {

		ProcessResult result = new ProcessResult();
		SortedMap<String, String> paramsMap = new TreeMap<String, String>();

		Enumeration<String> paramNames = request.getAttributeNames();

		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();

			if (paramName.equals("sign")) {
				continue;
			}

			System.out.println(paramName);

			String paramValues = (String) request.getAttribute(paramName);

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
		sb.append("token=" + "ss");
		System.out.println("服务端签名" + sb.toString());

		return result;
	}

}
