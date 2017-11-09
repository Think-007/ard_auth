/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2017年11月8日	| LPF 	| 	create the file                       
 */

package com.thinker.auth.domain;

import java.io.Serializable;

/**
 * 
 * 类简要描述
 * 
 * <p>
 * 类详细描述
 * </p>
 * 
 * @author LPF
 * 
 */

public class UserInfoDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户id
	private String userId;

	// 用户名
	private String userName;

	// 用户电话
	private String telNumber;
	// 性别
	private int sex;
	// 账号状态
	private int status;
	// 级别
	private int level;
	// 头像缩略图地址
	private String thumbURL;
	// 头像地址
	private String headpicURL;
	// 角色
	private int roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getThumbURL() {
		return thumbURL;
	}

	public void setThumbURL(String thumbURL) {
		this.thumbURL = thumbURL;
	}

	public String getHeadpicURL() {
		return headpicURL;
	}

	public void setHeadpicURL(String headpicURL) {
		this.headpicURL = headpicURL;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleid() {
		return roleId;
	}

	public void setRoleid(int roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserInfoDetail [userId=" + userId + ", userName=" + userName
				+ ", telNumber=" + telNumber + ", sex=" + sex + ", status="
				+ status + ", level=" + level + ", thumbURL=" + thumbURL
				+ ", headpicURL=" + headpicURL + ", roleId=" + roleId + "]";
	}

}
