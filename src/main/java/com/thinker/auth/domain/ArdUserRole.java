package com.thinker.auth.domain;

import java.io.Serializable;

public class ArdUserRole implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//用户id
	private double userId;
	//角色id
	private int roleId;
	public double getUserId() {
		return userId;
	}
	public void setUserId(double userId) {
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
