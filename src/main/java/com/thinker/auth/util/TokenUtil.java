/*      						
 * Copyright 2012 LPF  All rights reserved.
 * 
 * History:
 * ------------------------------------------------------------------------------
 * Date    	|  Who  		|  What  
 * 2017年11月6日	| LPF 	| 	create the file                       
 */

package com.thinker.auth.util;

import org.apache.shiro.crypto.hash.Md5Hash;

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

public class TokenUtil {

	public static String generateToken(String userName) {

		String str = System.currentTimeMillis() + userName;

		Md5Hash token = new Md5Hash(str, "ard", 3);

		return token.toString();

	}

}
