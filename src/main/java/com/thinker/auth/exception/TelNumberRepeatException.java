package com.thinker.auth.exception;

/**
 * 手机号已注册异常
 * 
 * @author lipengfeia
 *
 */
public class TelNumberRepeatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelNumberRepeatException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TelNumberRepeatException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public TelNumberRepeatException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public TelNumberRepeatException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public TelNumberRepeatException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


}
