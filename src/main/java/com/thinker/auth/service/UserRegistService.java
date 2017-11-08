package com.thinker.auth.service;

import java.util.Map;

import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.domain.UserRegistParam;

public interface UserRegistService {

	/**
	 * 用户注册业务
	 * 
	 * @param userRegistParam
	 * @return
	 */
	public Map<String, Object> regitsUser(UserRegistParam userRegistParam,
			String salt, ArdUserRole ardUserRole) throws Exception;

}
