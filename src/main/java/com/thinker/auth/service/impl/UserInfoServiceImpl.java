package com.thinker.auth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thinker.auth.dao.ArdUserAttachMapper;
import com.thinker.auth.dao.ArdUserBmMapper;
import com.thinker.auth.dao.ArdUserMapper;
import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.UserInfoDetail;
import com.thinker.auth.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	// 用户信息查询
	@Resource
	private ArdUserMapper ardUserMapper;

	@Resource
	private ArdUserAttachMapper ardUserAttachMapper;

	// // 用户附属信息查询
	// @Resource
	// private ArdUserAttachMapper ardUserAttachMapper;
	//
	// // 用户别名信息查询
	// @Resource
	// private ArdUserBmMapper ardUserBmMapper;

	@Override
	public ArdUser getUserInfoByTelNumber(String telNumber) {

		ArdUser ardUser = ardUserMapper.queryArdUserByTelNumber(telNumber);

		return ardUser;
	}

	@Override
	public UserInfoDetail getUserInfoDetailByTelNumber(String telNumber) {

		UserInfoDetail userInfoDetail = ardUserMapper.queryArdUserDetailByTelNumber(telNumber);

		return userInfoDetail;
	}

	@Override
	public int updateUserHeadPic(ArdUserAttach ardUserAttach) {

		int result = ardUserAttachMapper.updateUserAttach(ardUserAttach);

		return result;
	}

	@Override
	public int updateUserInfo(ArdUser ardUser) {
		// TODO Auto-generated method stub

		int result = ardUserMapper.updateArdUser(ardUser);

		return result;
	}
}
