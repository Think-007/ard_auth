package com.thinker.auth.service;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAttach;
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

	/**
	 * 根据uid更新头像信息
	 * 
	 * @param userId
	 * @return
	 */
	public int updateUserHeadPic(ArdUserAttach ardUserAttach);

	/**
	 * 更新用户信息
	 * 
	 * @param ardUser
	 * @return
	 */
	public int updateUserInfo(ArdUser ardUser);

}
