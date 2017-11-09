package com.thinker.auth.dao;

import java.util.Map;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.UserInfoDetail;

public interface ArdUserMapper {

	/**
	 * 添加用户
	 * 
	 * @param ardUser
	 * @return
	 */
	public int insertArdUser(ArdUser ardUser);

	/**
	 * 根据电话号查询用户信息
	 * 
	 * @param telNumber
	 * @return
	 */
	public ArdUser queryArdUserByTelNumber(String telNumber);

	/**
	 * 根据电话号码查询用户详情
	 * 
	 * @param telNumber
	 * @return
	 */
	public UserInfoDetail queryArdUserDetailByTelNumber(String telNumber);

	// public int deleteArdUser(ArdUser ardUser);
	//
	// public int updateArdUser(ArdUser ardUser);
	//
	// public int queryArdUser(ArdUser ardUser);

}
