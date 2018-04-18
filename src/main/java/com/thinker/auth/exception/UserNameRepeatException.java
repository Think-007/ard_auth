package com.thinker.auth.exception;

/**
 * 用户名重复异常
 * 
 * @author lipengfeia
 *
 */
public class UserNameRepeatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNameRepeatException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserNameRepeatException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UserNameRepeatException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserNameRepeatException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UserNameRepeatException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}



}
