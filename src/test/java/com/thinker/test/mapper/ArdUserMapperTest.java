package com.thinker.test.mapper;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinker.auth.dao.ArdUserMapper;
import com.thinker.auth.domain.ArdUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArdUserMapperTest {

	@Autowired
	private ArdUserMapper ardUserMapper;

	@Test
	public void insert() {
		ArdUser ardUser = new ArdUser();
		ardUser.setUserId("fsdafasd ");
		ardUser.setPassword("1111");
		ardUser.setCreateTime(Calendar.getInstance().getTime());
		ardUserMapper.insertArdUser(ardUser);
	}

	@Test
	public void update() {
		ArdUser ardUser = new ArdUser();
		ardUser.setUserId("fsdafasd");
		ardUser.setPassword("fddddddddddddsafd");

		ardUserMapper.updateArdUser(ardUser);
	}

	@Test
	public void updateStatus() {

		ardUserMapper.updateArdUserStatus("fsdafasd", 4);
	}

	@Test
	public void queryArdUserByuserId() {

		ArdUser a = ardUserMapper.queryArdUserByuserId("fsdafasd ");

		System.out.println(a);

	}

}
