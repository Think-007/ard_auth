package com.thinker.auth.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.exception.PassWordErrorException;
import com.thinker.auth.exception.UserLockException;
import com.thinker.auth.exception.UserNotExistException;
import com.thinker.auth.service.AuthUserService;
import com.thinker.auth.service.UserInfoService;
import com.thinker.security.Base64;
import com.thinker.security.RSAEncrypt;
import com.thinker.util.ArdLog;
import com.thinker.util.ArdUserConst;
import com.thinker.util.CacheUtil;

@Service
public class AuthUserServiceImpl implements AuthUserService {
	private static final Logger logger = LoggerFactory
			.getLogger(AuthUserServiceImpl.class);
	// 加盐次数
	@Value("${salt.hashIterations}")
	private int hashIterations;
	@Resource
	private UserInfoService userInfoService;

	@Override
	public boolean authUser(String userName, String password) {

		// 根据电话号码查询用户id ,再查询用户信息

		ArdUser ardUser = userInfoService.getUserInfoByTelNumber(userName);

		// 用户不存在
		if (ardUser == null) {
			throw new UserNotExistException("用户不存在");
		}
		switch (ardUser.getStatus()) {
		case ArdUserConst.USER_LOCKED:
			throw new UserLockException("用户已被锁定");

		case ArdUserConst.USER_LOGOUT:
			throw new UserLockException("用户已被锁定");

		default:
			break;
		}

		// 数据库盐值
		String sqlPwd = ardUser.getPassword();
		String pwd = new Md5Hash(password, ardUser.getSalt(), hashIterations)
				.toString();
		if (!sqlPwd.equals(pwd)) {
			throw new PassWordErrorException("密码不正确");
		}
		return true;

	}

	/**
	 * 生成rsa密钥对
	 */
	@PostConstruct
	public void generateKeyPairs() {

		String[] keyPairs = RSAEncrypt.genKeyPair();

		System.out.println("public key : " + keyPairs[0]);

		System.out.println("private key : " + keyPairs[1]);

		CacheUtil.keyCache.put("publickey", keyPairs[0]);
		CacheUtil.keyCache.put("privatekey", keyPairs[1]);

	}

	@Override
	public String[] decryptReqStr(String encryptStr) throws Exception {

		ArdLog.info(logger, "decryptReqStr", null, "密文 ： " + encryptStr);
		String privateKey = CacheUtil.keyCache.get("privatekey");
		String userInfoStr = new String(RSAEncrypt.decrypt(
				RSAEncrypt.loadPrivateKeyByStr(privateKey),
				Base64.decode(encryptStr)));

		String[] userInfo = userInfoStr.split("_");
		return userInfo;
	}

}
