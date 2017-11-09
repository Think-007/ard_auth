package com.thinker.auth.dao;

import com.thinker.auth.domain.ArdUser;

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

	// public int deleteArdUser(ArdUser ardUser);
	//
	// public int updateArdUser(ArdUser ardUser);
	//
	// public int queryArdUser(ArdUser ardUser);

}
