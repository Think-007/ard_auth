package com.thinker.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinker.auth.dao.ArdUserAttachMapper;
import com.thinker.auth.dao.ArdUserMapper;
import com.thinker.auth.dao.ArdUserRoleMapper;
import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.domain.UserRegistParam;
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
	public Map<String, Object> regitsUser(UserRegistParam userRegistParam,
			String salt, ArdUserRole ardUserRole) throws Exception {
		ArdUser ardUser = new ArdUser();
		ardUser.setUserId(Math.random() * 1000000);
		ardUser.setUserName(userRegistParam.getUserName());
		ardUser.setPassword(userRegistParam.getPassword());
		ardUser.setSalt(salt);
		// 注册用户
		ardUserMapper.insertArdUser(ardUser);

		// 定位用户角色为普通用户
		ardUserRole.setUserId(ardUser.getUserId());
		ardUserRoleMapper.insertAruUserRole(ardUserRole);

		
		// 绑定用户电话信息
		ArdUserAttach ardUserAttach = new ArdUserAttach();
		ardUserAttach.setUserId(ardUser.getUserId());
		ardUserAttach.setTelNum(userRegistParam.getTelNumber());
		ardUserAttachMapper.insertUserAttach(ardUserAttach);

		Map<String, Object> userInfo = new HashMap<String, Object>();
		userInfo.put("userid", ardUser.getUserId());
		userInfo.put("username", ardUser.getUserName());
		userInfo.put("role", ardUserRole.getRoleId());
		return userInfo;
	}

}
