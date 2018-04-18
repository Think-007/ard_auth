package com.thinker.auth.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色模型
 * 
 * @author lipengfeia
 *
 */
public class ArdRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 角色id
	private int roleId;

	// 角色名称
	private String roleName;

	// 角色描述
	private String roleDesc;

	// 角色权限
	private List<ArdPermission> permissions;
	// 创建时间
	private Date createTime;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public List<ArdPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<ArdPermission> permissions) {
		this.permissions = permissions;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ArdRole [roleId=" + roleId + ", roleName=" + roleName
				+ ", roleDesc=" + roleDesc + ", permissions=" + permissions
				+ ", createTime=" + createTime + "]";
	}

}
