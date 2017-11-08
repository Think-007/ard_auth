package com.thinker.auth.service;

import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.domain.UserRegistParam;

public interface UserRegistService {

	/**
	 * 用户注册业务
	 * 
	 * @param userRegistParam
	 * @return
	 */
	public void regitsUser(UserRegistParam userRegistParam, String salt,
			ArdUserRole ardUserRole) throws Exception;

}
