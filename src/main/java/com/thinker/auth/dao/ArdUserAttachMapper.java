package com.thinker.auth.dao;

import java.util.List;

import com.thinker.auth.domain.ArdUserAttach;

public interface ArdUserAttachMapper {

	/**
	 * 添加用户附加信息
	 * 
	 * @param userid
	 * @return
	 */
	public int insertUserAttach(ArdUserAttach ardUserAttach);

	/**
	 * 根据uid更新用户信息
	 * 
	 * @param ardUserAttach
	 * @return
	 */
	public int updateUserAttach(ArdUserAttach ardUserAttach);

	/**
	 * 根据电话号差查询用户附加信息
	 * 
	 * @param telNumber
	 * @return
	 */
	public ArdUserAttach queryUserAttachByTelNum(String telNumber);

	/**
	 * 根据uid查询绑定信息列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<ArdUserAttach> queryUserAttachByUserId(String userId);

}
