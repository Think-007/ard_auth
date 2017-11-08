package com.thinker.auth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.thinker.auth.dao.ArdUserAttachMapper;
import com.thinker.auth.dao.ArdUserMapper;
import com.thinker.auth.dao.ArdUserRoleMapper;
import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.domain.UserRegistParam;
import com.thinker.auth.exception.TelNumberRepeatException;
import com.thinker.auth.exception.UserNameRepeatException;
import com.thinker.auth.service.UserRegistService;

@Service
public class UserRegistServiceImpl implements UserRegistService {

	@Resource
	private ArdUserMapper ardUserMapper;

	@Resource
	private ArdUserRoleMapper ardUserRoleMapper;

	@Resource
	private ArdUserAttachMapper ardUserAttachMapper;

	@Override
	@Transactional
	public void regitsUser(UserRegistParam userRegistParam, String salt,
			ArdUserRole ardUserRole) throws Exception {
		ArdUser ardUser = new ArdUser();
		ardUser.setUserId(Math.random() * 1000000);
		ardUser.setUserName(userRegistParam.getUserName());
		ardUser.setPassword(userRegistParam.getPassword());
		ardUser.setSalt(salt);
		// 注册用户
		try {
			ardUserMapper.insertArdUser(ardUser);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserNameRepeatException("用户名重复");
		}

		// 定位用户角色为普通用户
		ardUserRole.setUserId(ardUser.getUserId());
		ardUserRoleMapper.insertAruUserRole(ardUserRole);

		// 绑定用户电话信息
		ArdUserAttach ardUserAttach = new ArdUserAttach();
		ardUserAttach.setUserId(ardUser.getUserId());
		ardUserAttach.setTelNum(userRegistParam.getTelNumber());
		try {
			ardUserAttachMapper.insertUserAttach(ardUserAttach);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new TelNumberRepeatException("手机号重复");
		}

	}

}
