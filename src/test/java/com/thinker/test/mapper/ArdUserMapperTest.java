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
	public void test() {
		ArdUser ardUser = new ArdUser();
		ardUser.setUserId("fsdafasd ");
		ardUser.setCreateTime(Calendar.getInstance().getTime());
		ardUserMapper.insertArdUser(ardUser);
	}

}
