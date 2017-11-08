package com.thinker.auth.domain;

import java.io.Serializable;

public class ArdUserRole implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//用户id
	private String userId;
	//角色id
	private int roleId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return "ArdUserRole [userId=" + userId + ", roleId=" + roleId + "]";
	}

	
}
