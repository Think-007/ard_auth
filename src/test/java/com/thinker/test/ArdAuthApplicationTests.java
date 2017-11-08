package com.thinker.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinker.auth.dao.ArdUserMapper;
import com.thinker.auth.domain.ArdUser;
import com.thinker.auth.service.UserRegistService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArdAuthApplicationTests {

	@Resource
	private ArdUserMapper ardUserMapper;
	
	@Resource
	private UserRegistService userRegistService;

	@Test
	public void contextLoads() {
		try {
			ArdUser ardUser = new ArdUser();
			ardUser.setUserId("12334545");
			ardUserMapper.insertArdUser(ardUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
