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
import com.thinker.auth.exception.QqRepeatException;
import com.thinker.auth.exception.SinaRepeatException;
import com.thinker.auth.exception.TelNumberRepeatException;
import com.thinker.auth.exception.UserNameRepeatException;
import com.thinker.auth.exception.WeiChatRepeatException;
import com.thinker.auth.service.UserRegistService;
import com.thinker.creator.domain.ProcessResult;
import com.thinker.util.ArdConst;
import com.thinker.util.ArdUserConst;

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
		// 如果不是手机号注册，要在后面加随机数
		if (regitsType == ArdUserConst.PHONE) {
			ardUserBm.setUserName(userRegistParam.getUserName());
		} else {
			ardUserBm.setUserName(userRegistParam.getUserName() + Math.random()
					* 10000);
		}
		ardUserBm.setCreateTime(createTime);
		try {
			ardUserBmMapper.insertUserBm(ardUserBm);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserNameRepeatException("用户名重复");
		}
		// 3.绑定用户信息
		bindThirdInfo(userRegistParam, regitsType, userId,ArdConst.MAIN_ATTACH, createTime);

		// 4.定位用户角色
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

	/**
	 * 
	 * @param userRegistParam
	 *            注册参数
	 * @param regitsType
	 *            注册方式
	 * @param userId
	 *            用户id
	 * @param mainAttach
	 *            0一般绑定账号 1主账号
	 * @param createTime
	 *            注册时间
	 * @return
	 */
	public ProcessResult bindThirdInfo(UserRegistParam userRegistParam,
			int regitsType, String userId, int mainAttach, Date createTime) {

		ProcessResult processResult = new ProcessResult();

		ArdUserAttach ardUserAttach = new ArdUserAttach();
		ardUserAttach.setUserId(userId);
		ardUserAttach.setTelNum(userRegistParam.getTelNumber());
		ardUserAttach.setThumbURL(userRegistParam.getHeadPicUrl());
		ardUserAttach.setHeadpicURL(userRegistParam.getHeadPicUrl());
		ardUserAttach.setCreateTime(createTime);
		ardUserAttach.setType(regitsType);
		ardUserAttach.setMainAttach(mainAttach);
		switch (regitsType) {
		case ArdUserConst.PHONE:
			try {
				ardUserAttachMapper.insertUserAttach(ardUserAttach);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new TelNumberRepeatException("手机号重复");
			}
			break;
		case ArdUserConst.WEICHAT:
			try {
				ardUserAttachMapper.insertUserAttach(ardUserAttach);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new WeiChatRepeatException("微信已经被绑定");
			}
			break;
		case ArdUserConst.QQ:
			try {
				ardUserAttachMapper.insertUserAttach(ardUserAttach);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new QqRepeatException("qq已经被绑定");
			}
			break;
		case ArdUserConst.SINA:
			try {
				ardUserAttachMapper.insertUserAttach(ardUserAttach);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new SinaRepeatException("sina已经被绑定");
			}
			break;

		default:
			break;
		}

		processResult.setRetCode(ProcessResult.SUCCESS);
		return processResult;
	}

	// @Override
	// public void thirdRegistUser(UserRegistParam userRegistParam, String salt,
	// int roleId) throws Exception {
	// // 1.分配用户uid
	// String userId = (new Double(Math.random() * 1000000000)).longValue()
	// + "";
	// Date createTime = Calendar.getInstance().getTime();
	// // 2 .创建用户别名
	// ArdUserBm ardUserBm = new ArdUserBm();
	// ardUserBm.setUserId(userId);
	// ardUserBm.setUserName(userRegistParam.getUserName() + Math.random()
	// * 10000);
	// ardUserBm.setCreateTime(createTime);
	// try {
	// ardUserBmMapper.insertUserBm(ardUserBm);
	// } catch (Exception e) {
	// e.printStackTrace();
	// throw new UserNameRepeatException("用户名重复");
	// }
	// // 3.绑定用户电话信息
	// ArdUserAttach ardUserAttach = new ArdUserAttach();
	// ardUserAttach.setUserId(userId);
	// ardUserAttach.setTelNum(userRegistParam.getTelNumber());
	// ardUserAttach.setThumbURL(userRegistParam.getHeadPicUrl());
	// ardUserAttach.setHeadpicURL(userRegistParam.getHeadPicUrl());
	// ardUserAttach.setCreateTime(createTime);
	// try {
	// ardUserAttachMapper.insertUserAttach(ardUserAttach);
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// throw new TelNumberRepeatException("手机号重复");
	// }
	//
	// // 4.定位用户角色为普通用户
	// ArdUserRole ardUserRole = new ArdUserRole();
	// ardUserRole.setUserId(userId);
	// ardUserRole.setRoleId(roleId);
	// ardUserRole.setCreateTime(createTime);
	// ardUserRoleMapper.insertAruUserRole(ardUserRole);
	//
	// // 5.开通用户的积分账户
	// ArdUserAccount ardUserAccount = new ArdUserAccount();
	// ardUserAccount.setUserId(userId);
	// ardUserAccount.setBalance(0);
	// ardUserAccount.setAccountType(ArdConst.BONUS);
	// ardUserAccountMapper.insertArdUserAccount(ardUserAccount);
	//
	// // 6.注册用户
	// ArdUser ardUser = new ArdUser();
	// ardUser.setUserId(userId);
	// ardUser.setPassword(userRegistParam.getPassword());
	// ardUser.setSalt(salt);
	// ardUser.setCreateTime(createTime);
	//
	// ardUserMapper.insertArdUser(ardUser);
	// }

}
