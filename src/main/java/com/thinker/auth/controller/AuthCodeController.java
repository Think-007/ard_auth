package com.thinker.auth.controller;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.think.creator.domain.ProcessResult;
import com.thinker.auth.util.ArdLog;
import com.thinker.auth.util.SecurityCode;
import com.thinker.auth.util.SecurityImage;

@RestController
@RequestMapping("/code")
public class AuthCodeController {

	private static final Logger logger = LoggerFactory
			.getLogger(AuthCodeController.class);

	private static final Map<String, String> redis = new HashMap<String, String>();

	@RequestMapping(value = "/securcode", method = RequestMethod.GET)
	public void generateSecureCode(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {

		ArdLog.info(logger, "enter generateSecureCode", null, null);

		String securityCode = SecurityCode.getSecurityCode();

		BufferedImage bufferedImage = SecurityImage.createImage(securityCode);

		// 将四位数字的验证码保存到Session中。
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("code", securityCode);

		// // 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 将图像输出到输出流中。
		OutputStream os = response.getOutputStream();
		ImageIO.write(bufferedImage, "jpeg", os);
		os.close();
		ArdLog.info(logger, "finish generateSecureCode", null, "securityCode: "
				+ securityCode);
	}

	/**
	 * 向指定号码发送验证码
	 * 
	 * @param telNumber
	 * @return
	 */
	@RequestMapping(value = "/smscode", method = RequestMethod.GET)
	public ProcessResult generateSmsCode(HttpServletRequest request,
			HttpServletResponse response, String telNumber) {

		ArdLog.debug(logger, "enter generateSmsCode", null, telNumber);

		ProcessResult processResult = new ProcessResult();
		String smsCode = "123456";
		String clientType = request.getHeader("client");
		if (clientType == null) {

			// 将短信验证码保存到Session中。
			Session session = SecurityUtils.getSubject().getSession();
			session.setAttribute("smscode", smsCode);
		} else {

			redis.put(telNumber, smsCode);

		}

		processResult.setRetCode(0);
		ArdLog.debug(logger, "finish generateSmsCode", null, "processResult: "
				+ processResult);
		return processResult;

	}

	/**
	 * app注册时短信验证码校验
	 * 
	 * @param smsCode
	 * @return
	 */
	@RequestMapping(value = "/authentication", method = RequestMethod.GET)
	public ProcessResult authSmsCode(String telnum, String smsCode) {
		ArdLog.info(logger, "enter authSmsCode", null, smsCode);

		ProcessResult processResult = new ProcessResult();
		String code = (String) redis.get(telnum);

		// 校验短信验证码是否正确
		if (smsCode != null && smsCode.equals(code)) {
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			redis.remove(telnum);
		} else {
			processResult.setRetCode(ProcessResult.FAILED);
			processResult.setRetMsg("failed");
		}
		ArdLog.info(logger, "finish authSmsCode", null, processResult);
		return processResult;

	}
}
