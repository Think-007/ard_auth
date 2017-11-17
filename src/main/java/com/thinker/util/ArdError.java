package com.thinker.util;

public interface ArdError {

	// 异常错误码
	public static final int EXCEPTION = -1000;
	public static final String EXCEPTION_MSG = "异常";

	// *********************登录错误*************************

	// 用户名重复
	public static final int NAME_REPEAT = 10001;
	// 用户不存在
	public static final int USER_NOT_EXIST = 10003;
	// 密码错误
	public static final int PASSWORD_ERROR = 10002;
	// 用户被锁定
	public static final int USER_LOCKED = 10004;
	// 用户注销
	public static final int ACCOUNT_LOGOUT = 10005;
	// 验证码错误
	public static final int SMS_CODE_ERROR = 10006;
	// 电话号码已经注册
	public static final int TEL_NUM_REGISTED = 10007;

	// ******************拦截请求错误********************

	// url过期
	public static final int URL_TIME_OUT = 20001;
	// token过期
	public static final int TOKEN_TIME_OUT = 20002;
	// 签名不对，参数不合法。
	public static final int PARAM_ILLEGAL = 20003;
	// 自动登录令牌过期
	public static final int AUTO_LOGIN_TOKEN_TIME_OUT = 20004;

}
