package com.thinker.auth.service.impl;

import javax.annotation.Resource;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.exception.PassWordErrorException;
import com.thinker.auth.exception.UserLockException;
import com.thinker.auth.exception.UserNotExistException;
import com.thinker.auth.service.AuthUserService;
import com.thinker.auth.service.UserInfoService;
import com.thinker.auth.util.UserStatus;

@Service
public class AuthUserServiceImpl implements AuthUserService {

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
		case UserStatus.USER_LOCKED:
			throw new UserLockException("用户已被锁定");

		case UserStatus.USER_LOGOUT:
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
}
