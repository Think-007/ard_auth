package com.thinker.auth.domain;

import java.io.Serializable;
import java.util.Date;

public class ArdUserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用户id
	private String userId;
	// 角色id
	private int roleId;

	// 创建时间
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ArdUserRole [userId=" + userId + ", roleId=" + roleId
				+ ", createTime=" + createTime + "]";
	}

}
