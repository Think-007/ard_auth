package com.thinker.auth.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

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

	/**
	 * 根据useId查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public ArdUser queryArdUserByuserId(String userId);

//	/**
//	 * 根据电话号码查询用户详情
//	 * 
//	 * @param telNumber
//	 * @return
//	 */
//	public UserInfoDetail queryArdUserDetailByTelNumber(String telNumber);

	/**
	 * 根据uid更新用户信息
	 * 
	 * @param ardUser
	 * @return
	 */
	public int updateArdUser(ArdUser ardUser);

	/**
	 * 根据uid更新用户账号状态
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	public int updateArdUserStatus(@Param("userId") String userId,
			@Param("status") int status);

	// public int deleteArdUser(ArdUser ardUser);
	//
	// public int updateArdUser(ArdUser ardUser);
	//
	// public int queryArdUser(ArdUser ardUser);

}
