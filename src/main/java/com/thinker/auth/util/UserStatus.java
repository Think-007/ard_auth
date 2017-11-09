package com.thinker.auth.util;

public interface UserStatus {

	// 正常
	public static final int NORMAL = 0;

	// 已经实名
	public static final int REAL_NAME = 1;

	// 锁定
	public static final int USER_LOCKED = 2;
	// 注销
	public static final int USER_LOGOUT = -1;

}
