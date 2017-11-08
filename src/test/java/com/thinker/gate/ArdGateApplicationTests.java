package com.thinker.gate;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinker.auth.dao.ArdUserMapper;
import com.thinker.auth.domain.ArdUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArdGateApplicationTests {

	@Resource
	private ArdUserMapper ardUserMapper;

	@Test
	public void contextLoads() {
		try {
			ArdUser ardUser = new ArdUser();
			ardUser.setUserId(3);
			ardUser.setUserName("333");

			ardUserMapper.insertArdUser(ardUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
