package com.thinker.auth.dao;

import org.apache.ibatis.annotations.Param;

import com.thinker.auth.domain.ArdUserRole;

public interface ArdUserRoleMapper {
	/**
	 * 存储用户角色关联信息
	 * 
	 * @param ardUserRole
	 * @return
	 */
	public int insertAruUserRole(ArdUserRole ardUserRole);

	/**
	 * 根据uid查询用户角色信息
	 * 
	 * @param userId
	 * @return
	 */
	public ArdUserRole queryAruUserRoleByUserId(@Param("userId") String userId);

}
