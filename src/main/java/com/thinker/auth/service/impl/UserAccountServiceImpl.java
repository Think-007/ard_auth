package com.thinker.auth.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thinker.auth.dao.ArdUserAccountMapper;
import com.thinker.auth.domain.ArdUserAccount;
import com.thinker.auth.service.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Resource
	private ArdUserAccountMapper ardUserAccountMapper;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	public ArdUserAccount getUserAccountInfoByUserId(String userId) {
		// TODO Auto-generated method stub

		ArdUserAccount ardUserAccount = ardUserAccountMapper
				.queryArdUserAccountByUserId(userId);
		return ardUserAccount;
	}

	@Override
	public int updateUseAccountInfoByUseId(String userId, double bonus) {
		// TODO Auto-generated method stub
		ArdUserAccount ardUserAccount = ardUserAccountMapper
				.queryArdUserAccountByUserId(userId);

		Date today = Calendar.getInstance().getTime();

		if (sdf.format(ardUserAccount.getUpdateTime())
				.equals(sdf.format(today))) {
			return 0;
		}

		ardUserAccount.setBalance(ardUserAccount.getBalance() + bonus);

		int result = ardUserAccountMapper.updateArdUserAccount(ardUserAccount);

		return result;
	}

}
