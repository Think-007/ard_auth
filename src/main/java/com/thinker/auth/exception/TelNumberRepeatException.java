package com.thinker.auth.exception;

/**
 * 手机号已注册异常
 * 
 * @author lipengfeia
 *
 */
public class TelNumberRepeatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelNumberRepeatException() {
		super();
	}

	public TelNumberRepeatException(String msg) {
		super(msg);
	}

}
