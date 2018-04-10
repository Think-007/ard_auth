package com.thinker.auth.service;

import java.util.List;

import com.thinker.auth.domain.ArdUserAccount;

/**
 * 用户账户业务
 * 
 * @author lipengfeia
 *
 */
public interface UserAccountService {

	/**
	 * 根据uid插叙用户账户列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<ArdUserAccount> getUserAccountList(String userId);

	/**
	 * 根据uid给账户增加积分
	 * 
	 * @param userId
	 * @param bonus
	 * @return
	 */
	public ArdUserAccount updateUseAccountInfoByUseId(String userId, double bonus);

}
