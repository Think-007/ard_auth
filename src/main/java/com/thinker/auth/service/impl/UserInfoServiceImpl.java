package com.thinker.auth.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thinker.auth.dao.ArdUserMapper;
import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	private ArdUserMapper ardUserMapper;

	@Override
	public ArdUser getUserInfoByTelNumber(String telNumber) {

		ArdUser ardUser = ardUserMapper.queryArdUserByTelNumber(telNumber);

		return ardUser;
	}

}
