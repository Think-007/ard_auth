package com.thinker.test.exception;

import com.thinker.auth.exception.UserNameRepeatException;


public class Test {

	public static void main(String[] args) {

		try {
			
			throw new UserNameRepeatException("用户名重复");
		} catch (UserNameRepeatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
