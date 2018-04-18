package com.thinker.test;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

	// @Resource
	// private JedisCluster jedisCluster;
	//
	// @Test
	// public void testJedis() {
	//
	// jedisCluster.set("afsda", "4354");
	// }

	private MockMvc mvc;
	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();// 建议使用这种
	}

	@Test
	public void testmvc() {

		try {
			String uri = "/auth/code/publickey";
			MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders
					.get(uri).header("uid", "ss")
					.header("timestamp", System.currentTimeMillis())
					.accept(MediaType.APPLICATION_JSON);

			MvcResult mvcResult = mvc.perform(mockRequestBuilder).andReturn();

			int status = mvcResult.getResponse().getStatus();

			Assert.assertTrue(200 == status);
			
			String content = mvcResult.getResponse().getContentAsString();

			System.out.println(content);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
