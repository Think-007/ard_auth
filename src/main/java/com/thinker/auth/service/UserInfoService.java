package com.thinker.auth.service;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.UserInfoDetail;

public interface UserInfoService {

	/**
	 * 根据电话号码查询用户信息
	 * 
	 * @param telNumber
	 * @return
	 */
	public ArdUser getUserInfoByTelNumber(String telNumber);

	/**
	 * 根据电话号码查询用户详细信息
	 * 
	 * @param telNumber
	 * @return
	 */
	public UserInfoDetail getUserInfoDetailByTelNumber(String telNumber);

}
