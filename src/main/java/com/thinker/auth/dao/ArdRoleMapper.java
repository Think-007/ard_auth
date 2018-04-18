package com.thinker.auth.dao;

import com.thinker.auth.domain.ArdRole;

public interface ArdRoleMapper {

	/**
	 * 添加角色
	 * 
	 * @param ardRole
	 * @return
	 */
	public int insertArdRole(ArdRole ardRole);

	public int deleteArdRole(ArdRole ardRole);

	public int updateArdRole(ArdRole ardRole);

	public int queryArdRole(ArdRole ardRole);

}
