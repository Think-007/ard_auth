package com.thinker.auth.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thinker.auth.dao.ArdUserAccountMapper;
import com.thinker.auth.domain.ArdUserAccount;
import com.thinker.auth.service.UserAccountService;
import com.thinker.util.ArdConst;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Resource
	private ArdUserAccountMapper ardUserAccountMapper;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	public List<ArdUserAccount> getUserAccountList(String userId) {

		List<ArdUserAccount> ardUserAccount = ardUserAccountMapper
				.queryArdUserAccountByUserId(userId);
		return ardUserAccount;
	}

	@Override
	public ArdUserAccount updateUseAccountInfoByUseId(String userId,
			double bonus) {
		ArdUserAccount ardUserAccount = ardUserAccountMapper
				.queryArdUserAccountByUserIdAndType(userId, ArdConst.BONUS);

		Date today = Calendar.getInstance().getTime();

		if (sdf.format(ardUserAccount.getUpdateTime())
				.equals(sdf.format(today))) {
			return null;
		}

		ardUserAccount.setBalance(ardUserAccount.getBalance() + bonus);

		ardUserAccountMapper.updateArdUserAccount(ardUserAccount);

		return ardUserAccount;
	}

}
