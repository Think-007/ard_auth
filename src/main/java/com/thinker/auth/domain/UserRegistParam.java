package com.thinker.auth.domain;

import java.io.Serializable;

public class UserRegistParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户名
	private String userName;
	// 密码
	private String password;
	// 手机号
	private String telNumber;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	@Override
	public String toString() {
		return "UserRegistParam [userName=" + userName + ", password="
				+ password + ", telNumber=" + telNumber + "]";
	}

}
