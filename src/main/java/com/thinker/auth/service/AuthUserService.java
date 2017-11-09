package com.thinker.auth.service;

public interface AuthUserService {

	/**
	 * 用户认证
	 * 
	 * @param userName
	 * @param password
	 */
	public boolean authUser(String userName, String password);

}
