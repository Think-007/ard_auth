package com.thinker.auth.dao;

import com.thinker.auth.domain.ArdUserAccount;

public interface ArdUserAccountMapper {

	/**
	 * 账户信息创建
	 * 
	 * @param ardUserAccount
	 * @return
	 */
	public int insertArdUserAccount(ArdUserAccount ardUserAccount);

	/**
	 * 根据uid擦护心用户积分账户信息
	 * 
	 * @param userId
	 * @return
	 */
	public ArdUserAccount queryArdUserAccountByUserId(String userId);

	/**
	 * 更新用户积分账户信息
	 * 
	 * @param ardUserAccount
	 * @return
	 */
	public int updateArdUserAccount(ArdUserAccount ardUserAccount);

}
