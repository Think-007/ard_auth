/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2017年11月8日	| LPF 	| 	create the file                       
 */

package com.thinker.auth.domain;

import java.io.Serializable;

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

public class UserInfoDetail implements Serializable{
	
	//用户信息
	private ArdUser ardUser;
	//别名信息
	private ArdUserBm ardUserBm;
	//头像电话等附加信息
	private ArdUserAttach ardUserAttach;

}
