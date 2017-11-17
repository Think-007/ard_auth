package com.thinker.auth.service;

import com.thinker.auth.domain.ArdUserAccount;

/**
 * 用户账户业务
 * 
 * @author lipengfeia
 *
 */
public interface UserAccountService {

	/**
	 * 根据uid查询用户账户信息
	 * 
	 * @param userId
	 * @return
	 */
	public ArdUserAccount getUserAccountInfoByUserId(String userId);

	/**
	 * 根据uid给账户增加积分
	 * 
	 * @param userId
	 * @param bonus
	 * @return
	 */
	public int updateUseAccountInfoByUseId(String userId, double bonus);

}
