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

	// 登录令牌
	public static String generateLoginToken(String randInfo) {

		String str = System.currentTimeMillis() + randInfo;

		Md5Hash token = new Md5Hash(str, "ard", 3);

		return token.toString();

	}

	// 长连接token
	public static String generateToken() {

		String str = System.currentTimeMillis() + Math.random() * 10000 + "";

		Md5Hash token = new Md5Hash(str, "ard", 3);

		return token.toString();

	}

}
