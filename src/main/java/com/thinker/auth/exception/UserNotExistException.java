package com.thinker.auth.exception;

public class UserNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserNotExistException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UserNotExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserNotExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UserNotExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
