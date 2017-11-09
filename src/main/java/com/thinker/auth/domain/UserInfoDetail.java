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

public class UserInfoDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用户信息
	private ArdUser ardUser;
	// 别名信息
	private ArdUserBm ardUserBm;
	// 头像电话等附加信息
	private ArdUserAttach ardUserAttach;
	

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

	public ArdUserAttach getArdUserAttach() {
		return ardUserAttach;
	}

	public void setArdUserAttach(ArdUserAttach ardUserAttach) {
		this.ardUserAttach = ardUserAttach;
	}

	@Override
	public String toString() {
		return "UserInfoDetail [ardUser=" + ardUser + ", ardUserBm="
				+ ardUserBm + ", ardUserAttach=" + ardUserAttach + "]";
	}

}
