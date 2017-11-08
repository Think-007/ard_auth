package com.thinker.auth.exception;

/**
 * 用户名重复异常
 * 
 * @author lipengfeia
 *
 */
public class UserNameRepeatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNameRepeatException() {

		super();

	}

	public UserNameRepeatException(String msg) {

		super(msg);

	}

}
