package com.thinker.auth.domain;

import java.io.Serializable;
import java.util.Date;

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
	
	//创建时间
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ArdRolePermission [roleId=" + roleId + ", sourceId=" + sourceId
				+ ", createTime=" + createTime + "]";
	}


}
