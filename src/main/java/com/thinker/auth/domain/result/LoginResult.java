/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2017年11月16日	| LPF 	| 	create the file                       
 */

package com.thinker.auth.domain.result;

import java.io.Serializable;
import java.util.List;

import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAccount;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.ArdUserBm;
import com.thinker.auth.domain.ArdUserRole;

/**
 * 
 * 登录成功后返回客户结果模型
 * 
 * <p>
 * 类详细描述
 * </p>
 * 
 * @author LPF
 * 
 */

public class LoginResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 用户信息
	private ArdUser ardUser;

	// 用户别名信息
	private ArdUserBm ardUserBm;

	// 用户角色信息
	private ArdUserRole ardUserRole;

	// 用户绑定信息列表
	private List<ArdUserAttach> bindList;

	// 签到信息和支付账户信息
	private List<ArdUserAccount> accountList;

	// 用户token 加密后信息
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ArdUser getArdUser() {
		return ardUser;
	}

	public void setArdUser(ArdUser ardUser) {
		this.ardUser = ardUser;
	}

	public ArdUserBm getArdUserBm() {
		return ardUserBm;
	}

	public void setArdUserBm(ArdUserBm ardUserBm) {
		this.ardUserBm = ardUserBm;
	}

	public ArdUserRole getArdUserRole() {
		return ardUserRole;
	}

	public void setArdUserRole(ArdUserRole ardUserRole) {
		this.ardUserRole = ardUserRole;
	}

	public List<ArdUserAttach> getBindList() {
		return bindList;
	}

	public void setBindList(List<ArdUserAttach> bindList) {
		this.bindList = bindList;
	}

	public List<ArdUserAccount> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<ArdUserAccount> accountList) {
		this.accountList = accountList;
	}

	@Override
	public String toString() {
		return "LoginResult [ardUser=" + ardUser + ", ardUserBm=" + ardUserBm
				+ ", ardUserRole=" + ardUserRole + ", bindList=" + bindList
				+ ", accountList=" + accountList + ", token=" + token + "]";
	}

}
