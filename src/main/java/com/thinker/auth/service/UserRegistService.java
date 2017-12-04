package com.thinker.auth.service;

import com.thinker.auth.domain.UserRegistParam;

public interface UserRegistService {

	/**
	 * 用户注册业务
	 * 
	 * @param userRegistParam
	 *            注册参数
	 * @param salt
	 *            盐值
	 * @param roleId
	 *            注册角色
	 * @param regitsType
	 *            注册方式
	 * @throws Exception
	 */
	public void regitsUser(UserRegistParam userRegistParam, String salt,
			int roleId, int regitsType) throws Exception;

	public void thirdRegistUser(UserRegistParam userRegistParam, String salt,
			int roleId) throws Exception;
}
