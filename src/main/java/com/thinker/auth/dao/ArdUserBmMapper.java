/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2017年11月8日	| LPF 	| 	create the file                       
 */

package com.thinker.auth.dao;

import org.apache.ibatis.annotations.Param;

import com.thinker.auth.domain.ArdUserBm;

/**
 * 
 * 类简要描述
 * 
 * <p>
 * 类详细描述
 * </p>
 * 
 * @author LPF
 * 
 */

public interface ArdUserBmMapper {

	// 创建用户名
	public int insertUserBm(ArdUserBm ardUserBm);

	/**
	 * 更改用户名
	 * 
	 * @param ardUserBm
	 * @return
	 */
	public int updateUserBm(ArdUserBm ardUserBm);

	/**
	 * 根据uid查询用户别名信息
	 * 
	 * @param userId
	 * @return
	 */
	public ArdUserBm queryUserBmByUid(@Param("userId") String userId);

}
