package com.thinker.auth.domain;

import java.io.Serializable;

/**
 * 角色权限关联类
 * @author lipengfeia
 *
 */
public class ArdRolePermission implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//角色id
	private int roleId;
	
	//权限id
	private int sourceId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	@Override
	public String toString() {
		return "ArdRolePermission [roleId=" + roleId + ", sourceId=" + sourceId
				+ "]";
	}
	

}
