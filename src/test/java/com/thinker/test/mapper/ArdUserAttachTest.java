package com.thinker.test.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinker.auth.dao.ArdUserAttachMapper;
import com.thinker.auth.domain.ArdUserAttach;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArdUserAttachTest {

	@Autowired
	private ArdUserAttachMapper mapper;

	@Test
	public void test() {

		ArdUserAttach a = new ArdUserAttach();
		a.setUserId(450190.4826065154 + "");
		a.setHeadpicURL("ffafasf");
		mapper.updateUserAttach(a);

	}

	@Test
	public void queryUserAttachByTelNum() {
		
		ArdUserAttach a = mapper.queryUserAttachByTelNum("18201410900");

		System.out.println(a);

	}
}
