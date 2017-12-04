package com.thinker.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinker.auth.util.Redis;
import com.thinker.creator.domain.ProcessResult;
import com.thinker.util.ArdConst;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;
import com.thinker.util.CacheUtil;

@RestController
@RequestMapping("/auth/code")
public class AuthCodeController {

	private static final Logger logger = LoggerFactory
			.getLogger(AuthCodeController.class);

	@RequestMapping("/publickey")
	public ProcessResult reqPublicKey() {
		
		ArdLog.debug(logger, "enter  reqPublicKey", null, null);
		ProcessResult processResult = new ProcessResult();

		String publicKey = CacheUtil.keyCache.get("publickey");

		if (publicKey != null) {

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			processResult.setRetObj(publicKey);
		}

		ArdLog.debug(logger, "finish reqPublicKey", null, processResult);
		return processResult;

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

			Redis.redis.put(ArdConst.PROJECT_FLAG+telNumber, smsCode);
			Redis.redis.put(ArdConst.PROJECT_FLAG+telNumber + "_auth", smsCode);

		}

		processResult.setRetCode(ProcessResult.SUCCESS);
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
	@RequestMapping(value = "/authentication", method = RequestMethod.POST)
	public ProcessResult authSmsCode(String telNumber, String smsCode) {
		ArdLog.info(logger, "enter authSmsCode", null, smsCode);

		ProcessResult processResult = new ProcessResult();

		String code = (String) Redis.redis.get(ArdConst.PROJECT_FLAG+telNumber);

		// 校验短信验证码是否正确
		if (smsCode != null && smsCode.equals(code)) {
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			Redis.redis.remove(ArdConst.PROJECT_FLAG+telNumber);
		} else {
			processResult.setRetCode(ArdError.SMS_CODE_ERROR);
			processResult.setRetMsg("验证码错误");
		}
		ArdLog.info(logger, "finish authSmsCode", null, processResult);
		return processResult;

	}
}
