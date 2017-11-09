///*      						
// * Copyright 2012 LPF  All rights reserved.
// * 
// * History:
// * ------------------------------------------------------------------------------
// * Date    	|  Who  		|  What  
// * 2017年10月26日	| LPF 	| 	create the file                       
// */
//
//package com.thinker.auth.shiro;
//
//import javax.annotation.Resource;
//
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.DisabledAccountException;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.apache.shiro.util.ByteSource;
//import org.springframework.stereotype.Service;
//
//import com.thinker.auth.domain.ArdUser;
//import com.thinker.auth.service.UserInfoService;
//import com.thinker.auth.util.UserStatus;
//
///**
// * 
// * shiro自定义鉴权
// * 
// * <p>
// * 类详细描述
// * </p>
// * 
// * @author LPF
// * 
// */
//@Service
//public class ShiroRealmImpl extends AuthorizingRealm {
//
//	@Resource
//	private UserInfoService userInfoService;
//
//	@Override
//	protected AuthorizationInfo doGetAuthorizationInfo(
//			PrincipalCollection principals) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(
//			AuthenticationToken token) throws AuthenticationException {
//
//		UsernamePasswordToken authToken = (UsernamePasswordToken) token;
//
//		String telNumber = authToken.getUsername();
//		String password = String.copyValueOf(authToken.getPassword());
//
//		// 根据电话号码查询用户id ,再查询用户信息
//
//		ArdUser ardUser = userInfoService.getUserInfoByTelNumber(telNumber);
//
//		// 用户不存在
//		if (ardUser == null) {
//			return null;
//		}
//		switch (ardUser.getStatus()) {
//		case UserStatus.USER_LOCKED:
//			break;
//
//		case UserStatus.USER_LOGOUT:
//			throw new DisabledAccountException("用户已注销");
//
//		default:
//			break;
//		}
//
//		// 数据库盐值
//		String sqlSalt = ardUser.getSalt();
//		System.out.println(ardUser);
//
//		return new SimpleAuthenticationInfo(telNumber, ardUser.getPassword(),
//				ByteSource.Util.bytes(sqlSalt), getName());
//
//	}
//}
