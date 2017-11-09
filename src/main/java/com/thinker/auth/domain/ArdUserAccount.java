package com.thinker.auth.domain;

import java.io.Serializable;

public class ArdUserAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户id
	private String userId;

	// 账户类型 0积分
	private int accountType;

	// 余额
	private double balance;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "ArdUserAccount [userId=" + userId + ", accountType="
				+ accountType + ", balance=" + balance + "]";
	}

}
