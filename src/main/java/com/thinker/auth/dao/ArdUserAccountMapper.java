package com.thinker.auth.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	 * 根据uid擦护心用户账户信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<ArdUserAccount> queryArdUserAccountByUserId(String userId);

	/**
	 * 根据uid和账号类型查询账户
	 * 
	 * @param userId
	 * @return
	 */
	public ArdUserAccount queryArdUserAccountByUserIdAndType(
			@Param("userId") String userId, @Param("type") int type);

	/**
	 * 更新用户积分账户信息
	 * 
	 * @param ardUserAccount
	 * @return
	 */
	public int updateArdUserAccount(ArdUserAccount ardUserAccount);

}
