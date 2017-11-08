package com.thinker.auth.dao;

import com.thinker.auth.domain.ArdUserRole;

public interface ArdUserRoleMapper {
	/**
	 * 存储用户角色关联信息
	 * 
	 * @param ardUserRole
	 * @return
	 */
	public int insertAruUserRole(ArdUserRole ardUserRole);

}
