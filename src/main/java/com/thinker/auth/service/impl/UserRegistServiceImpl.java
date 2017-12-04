package com.thinker.auth.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinker.auth.dao.ArdUserAccountMapper;
import com.thinker.auth.dao.ArdUserAttachMapper;
import com.thinker.auth.dao.ArdUserBmMapper;
import com.thinker.auth.dao.ArdUserMapper;
import com.thinker.auth.dao.ArdUserRoleMapper;
import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAccount;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.ArdUserBm;
import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.domain.UserRegistParam;
import com.thinker.auth.exception.TelNumberRepeatException;
import com.thinker.auth.exception.UserNameRepeatException;
import com.thinker.auth.service.UserRegistService;
import com.thinker.util.ArdConst;

@Service
public class UserRegistServiceImpl implements UserRegistService {

	@Resource
	private ArdUserMapper ardUserMapper;

	@Resource
	private ArdUserRoleMapper ardUserRoleMapper;

	@Resource
	private ArdUserAttachMapper ardUserAttachMapper;

	@Resource
	private ArdUserBmMapper ardUserBmMapper;

	@Resource
	private ArdUserAccountMapper ardUserAccountMapper;

	@Override
	@Transactional
	public void regitsUser(UserRegistParam userRegistParam, String salt,
			int roleId, int regitsType) throws Exception {

		// 1.分配用户uid
		String userId = (new Double(Math.random() * 1000000000)).longValue()
				+ "";
		Date createTime = Calendar.getInstance().getTime();
		// 2 .创建用户别名
		ArdUserBm ardUserBm = new ArdUserBm();
		ardUserBm.setUserId(userId);
		ardUserBm.setUserName(userRegistParam.getUserName());
		ardUserBm.setCreateTime(createTime);
		try {
			ardUserBmMapper.insertUserBm(ardUserBm);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserNameRepeatException("用户名重复");
		}
		// 3.绑定用户电话信息
		ArdUserAttach ardUserAttach = new ArdUserAttach();
		ardUserAttach.setUserId(userId);
		ardUserAttach.setTelNum(userRegistParam.getTelNumber());
		ardUserAttach.setThumbURL(userRegistParam.getHeadPicUrl());
		ardUserAttach.setHeadpicURL(userRegistParam.getHeadPicUrl());
		ardUserAttach.setCreateTime(createTime);
		try {
			ardUserAttachMapper.insertUserAttach(ardUserAttach);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new TelNumberRepeatException("手机号重复");
		}

		// 4.定位用户角色为普通用户
		ArdUserRole ardUserRole = new ArdUserRole();
		ardUserRole.setUserId(userId);
		ardUserRole.setRoleId(roleId);
		ardUserRole.setCreateTime(createTime);
		ardUserRoleMapper.insertAruUserRole(ardUserRole);

		// 5.开通用户的积分账户
		ArdUserAccount ardUserAccount = new ArdUserAccount();
		ardUserAccount.setUserId(userId);
		ardUserAccount.setBalance(0);
		ardUserAccount.setAccountType(ArdConst.BONUS);
		ardUserAccountMapper.insertArdUserAccount(ardUserAccount);

		// 6.注册用户
		ArdUser ardUser = new ArdUser();
		ardUser.setUserId(userId);
		ardUser.setPassword(userRegistParam.getPassword());
		ardUser.setSalt(salt);
		ardUser.setCreateTime(createTime);

		ardUserMapper.insertArdUser(ardUser);

	}

	@Override
	public void thirdRegistUser(UserRegistParam userRegistParam, String salt,
			int roleId) throws Exception {
		// 1.分配用户uid
		String userId = (new Double(Math.random() * 1000000000)).longValue()
				+ "";
		Date createTime = Calendar.getInstance().getTime();
		// 2 .创建用户别名
		ArdUserBm ardUserBm = new ArdUserBm();
		ardUserBm.setUserId(userId);
		ardUserBm.setUserName(userRegistParam.getUserName() + Math.random()
				* 10000);
		ardUserBm.setCreateTime(createTime);
		try {
			ardUserBmMapper.insertUserBm(ardUserBm);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserNameRepeatException("用户名重复");
		}
		// 3.绑定用户电话信息
		ArdUserAttach ardUserAttach = new ArdUserAttach();
		ardUserAttach.setUserId(userId);
		ardUserAttach.setTelNum(userRegistParam.getTelNumber());
		ardUserAttach.setThumbURL(userRegistParam.getHeadPicUrl());
		ardUserAttach.setHeadpicURL(userRegistParam.getHeadPicUrl());
		ardUserAttach.setCreateTime(createTime);
		try {
			ardUserAttachMapper.insertUserAttach(ardUserAttach);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new TelNumberRepeatException("手机号重复");
		}

		// 4.定位用户角色为普通用户
		ArdUserRole ardUserRole = new ArdUserRole();
		ardUserRole.setUserId(userId);
		ardUserRole.setRoleId(roleId);
		ardUserRole.setCreateTime(createTime);
		ardUserRoleMapper.insertAruUserRole(ardUserRole);

		// 5.开通用户的积分账户
		ArdUserAccount ardUserAccount = new ArdUserAccount();
		ardUserAccount.setUserId(userId);
		ardUserAccount.setBalance(0);
		ardUserAccount.setAccountType(ArdConst.BONUS);
		ardUserAccountMapper.insertArdUserAccount(ardUserAccount);

		// 6.注册用户
		ArdUser ardUser = new ArdUser();
		ardUser.setUserId(userId);
		ardUser.setPassword(userRegistParam.getPassword());
		ardUser.setSalt(salt);
		ardUser.setCreateTime(createTime);

		ardUserMapper.insertArdUser(ardUser);
	}

}
