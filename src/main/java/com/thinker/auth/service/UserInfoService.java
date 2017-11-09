package com.thinker.auth.service;

import com.thinker.auth.domain.ArdUser;

public interface UserInfoService {

	/**
	 * 根据电话号码查询用户信息
	 * 
	 * @param telNumber
	 * @return
	 */
	public ArdUser getUserInfoByTelNumber(String telNumber);

}
