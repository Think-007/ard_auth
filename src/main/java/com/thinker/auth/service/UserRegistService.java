package com.thinker.auth.service;

import java.util.Date;

import com.thinker.auth.domain.UserRegistParam;
import com.thinker.creator.domain.ProcessResult;

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

	/**
	 * 
	 * @param userRegistParam
	 *            注册参数
	 * @param regitsType
	 *            注册类型
	 * @param userId
	 *            用户id
	 * @param mainAttach
	 *            0一般绑定，1登录绑定
	 * @param createTime
	 *            注册时间
	 * @return
	 */
	public ProcessResult bindThirdInfo(UserRegistParam userRegistParam,
			int regitsType, String userId, int mainAttach, Date createTime);

}
