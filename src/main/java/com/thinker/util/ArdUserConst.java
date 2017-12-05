package com.thinker.util;

public interface ArdUserConst {

	/**
	 * *******************用户状态*********************
	 */
	/**
	 * 正常
	 */
	public static final int NORMAL = 0;

	/**
	 * 已经实名
	 */
	public static final int REAL_NAME = 1;

	/**
	 * 锁定
	 */
	public static final int USER_LOCKED = 2;
	/**
	 * 注销
	 */
	public static final int USER_LOGOUT = -1;

	/**
	 * ******************角色************************
	 */

	/**
	 * 普通用户
	 */
	public static final int NORMAL_USER = 0;

	/**
	 * **************************注册方式
	 */

	/**
	 * 手机注册
	 */
	public static final int PHONE = 0;
	/**
	 * 微信注册
	 */
	public static final int WEICHAT = 1;

	/**
	 * qq注册
	 */
	public static final int QQ = 2;

	/**
	 * 微博注册
	 */
	public static final int SINA = 3;

}
