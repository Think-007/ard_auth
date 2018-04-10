/**
 * 
 */
package com.thinker.auth.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 用户模型
 * 
 * @author lipengfeia
 *
 */
public class ArdUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户id
	private String userId;

	// 用户密码
	private String password;

	// 盐值
	private String salt;

	// 性别 0未知 1 男 2女
	private int sex;

	// 用户状态 0正常 1实名 2锁定 -1注销
	private int status;

	// 用户级别
	private int level;

	// 用户角色
	private List<ArdRole> roles;

	// 初次绑定的账号
	private ArdUserAttach mainAttach;

	// 是否设置密码，0没设置，1，已设置
	private int hasPassword;

	// 创建时间
	private Date createTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public List<ArdRole> getRoles() {
		return roles;
	}

	public void setRoles(List<ArdRole> roles) {
		this.roles = roles;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public ArdUserAttach getMainAttach() {
		return mainAttach;
	}

	public void setMainAttach(ArdUserAttach mainAttach) {
		this.mainAttach = mainAttach;
	}

	public int getHasPassword() {
		return hasPassword;
	}

	public void setHasPassword(int hasPassword) {
		this.hasPassword = hasPassword;
	}

	@Override
	public String toString() {
		return "ArdUser [userId=" + userId + ", password=" + password
				+ ", salt=" + salt + ", sex=" + sex + ", status=" + status
				+ ", level=" + level + ", roles=" + roles + ", mainAttach="
				+ mainAttach + ", hasPassword=" + hasPassword + ", createTime="
				+ createTime + "]";
	}

}
