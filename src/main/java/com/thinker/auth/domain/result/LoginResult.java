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

import com.thinker.auth.domain.UserInfoDetail;

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

	// 用户详细信息
	private UserInfoDetail userInfoDetail;
	// 用户token 加密后信息
	private String token;

	public UserInfoDetail getUserInfoDetail() {
		return userInfoDetail;
	}

	public void setUserInfoDetail(UserInfoDetail userInfoDetail) {
		this.userInfoDetail = userInfoDetail;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginResult [userInfoDetail=" + userInfoDetail + ", token=" + token + "]";
	}

}
