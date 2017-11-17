package com.thinker.auth.service;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.ArdUserBm;
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
	public ArdUserAttach updateUserHeadPic(String userId, String thumbURL,
			String headPicURL);

	/**
	 * 更新用户信息
	 * 
	 * @param ardUser
	 * @return
	 */
	public int updateUserPassword(String userId, String password);

	/**
	 * 根据uid查询用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public ArdUser getUserInfoByuserId(String userId);

	/**
	 * 根据uid更新别名
	 * 
	 * @param userId
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public ArdUserBm updateUserBm(String userId, String userName) throws Exception;

	/**
	 * 根据uid更新电话号码
	 * 
	 * @param userId
	 * @param telNumber
	 * @return
	 */
	public ArdUserAttach updaetTemNumberByUserId(String userId, String telNumber);

}
