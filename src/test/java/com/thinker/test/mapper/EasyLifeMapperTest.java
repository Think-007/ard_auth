package com.thinker.test.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.thinker.easylife.dao.EasyLifeMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EasyLifeMapperTest {

	@Autowired
	EasyLifeMapper mapper;

	@Test
	public void queryList() {
		List l = mapper.queryEasyLifeList();

		System.out.println(l);
	}
}
