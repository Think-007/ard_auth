package com.thinker.test.mapper;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinker.auth.dao.ArdUserAccountMapper;
import com.thinker.auth.domain.ArdUserAccount;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArdUserAccountMapperTest {

	@Resource
	private ArdUserAccountMapper ardUserAccountMapper;

	@Test
	public void test() {

		// insert
		// ArdUserAccount ardUserAccount = new ArdUserAccount();
		//
		// ardUserAccount.setUserId("ddddssss");
		// ardUserAccount.setBalance(9);
		// ardUserAccount.setAccountType(0);
		// int res = ardUserAccountMapper.insertArdUserAccount(ardUserAccount);
		//
		// Assert.assertTrue(1 == res);

		// query
		// ArdUserAccount a
		// =ardUserAccountMapper.queryArdUserAccountByUserId("ddddssss");

		// System.out.println(a);
	}

}
