package com.thinker.auth.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thinker.auth.dao.ArdUserAttachMapper;
import com.thinker.auth.dao.ArdUserBmMapper;
import com.thinker.auth.dao.ArdUserMapper;
import com.thinker.auth.dao.ArdUserRoleMapper;
import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.domain.ArdUserAttach;
import com.thinker.auth.domain.ArdUserBm;
import com.thinker.auth.domain.ArdUserRole;
import com.thinker.auth.exception.UserNameRepeatException;
import com.thinker.auth.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	// 用户信息查询
	@Resource
	private ArdUserMapper ardUserMapper;

	// 用户附加信息
	@Resource
	private ArdUserAttachMapper ardUserAttachMapper;

	// 用户别名设置
	@Resource
	private ArdUserBmMapper ardUserBmMapper;

	// 用户角色信息
	@Resource
	private ArdUserRoleMapper ardUserRoleMapper;

	// // 用户附属信息查询
	// @Resource
	// private ArdUserAttachMapper ardUserAttachMapper;
	//
	// // 用户别名信息查询
	// @Resource
	// private ArdUserBmMapper ardUserBmMapper;

	@Override
	public ArdUser getUserInfoByTelNumber(String telNumber) {

		ArdUser ardUser = ardUserMapper.queryArdUserByTelNumber(telNumber);

		return ardUser;
	}

	// @Override
	// public UserInfoDetail getUserInfoDetailByTelNumber(String telNumber) {
	//
	// UserInfoDetail userInfoDetail = ardUserMapper
	// .queryArdUserDetailByTelNumber(telNumber);
	//
	// return userInfoDetail;
	// }

	@Override
	public ArdUserAttach updateUserHeadPic(String userId, String thumbURL,
			String headPicURL) {
		ArdUserAttach ardUserAttach = new ArdUserAttach();
		ardUserAttach.setUserId(userId);
		ardUserAttach.setThumbURL(thumbURL);
		ardUserAttach.setHeadpicURL(headPicURL);
		ardUserAttachMapper.updateUserAttach(ardUserAttach);

		return ardUserAttach;
	}

	@Override
	public int updateUserPassword(String userId, String password) {
		// TODO Auto-generated method stub
		ArdUser ardUser = new ArdUser();
		ardUser.setUserId(userId);
		ardUser.setPassword(password);
		int result = ardUserMapper.updateArdUser(ardUser);

		return result;
	}

	@Override
	public ArdUser getUserInfoByuserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArdUserBm updateUserBm(String userId, String userName)
			throws Exception {
		// TODO Auto-generated method stub

		ArdUserBm bm = new ArdUserBm();
		bm.setUserId(userId);
		bm.setUserName(userName);
		int result;
		try {
			result = ardUserBmMapper.updateUserBm(bm);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserNameRepeatException("昵称重复");
		}
		return bm;
	}

	@Override
	public ArdUserAttach updateTemNumberByUserId(String userId, String telNumber) {
		// TODO Auto-generated method stub

		ArdUserAttach ardUserAttach = new ArdUserAttach();

		ardUserAttach.setUserId(userId);
		ardUserAttach.setTelNum(telNumber);
		ardUserAttachMapper.updateUserAttach(ardUserAttach);

		return ardUserAttach;
	}

	@Override
	public ArdUserBm getUserBmById(String userId) {

		ArdUserBm ardUserBm = ardUserBmMapper.queryUserBmByUid(userId);

		return ardUserBm;
	}

	@Override
	public ArdUserRole getArdUserRoleById(String userId) {

		ArdUserRole ardUserRole = ardUserRoleMapper
				.queryAruUserRoleByUserId(userId);
		return ardUserRole;
	}

	@Override
	public List<ArdUserAttach> getAttachListByUid(String userId) {

		List<ArdUserAttach> userAttachList = ardUserAttachMapper
				.queryUserAttachByUserId(userId);

		return userAttachList;
	}

	@Override
	public ArdUserAttach getAttachListByTelNumber(String telNumber) {

		ArdUserAttach ardUserAttach = ardUserAttachMapper
				.queryUserAttachByTelNum(telNumber);

		return ardUserAttach;
	}
}
