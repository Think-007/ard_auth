package com.thinker.test.controller;

import java.io.UnsupportedEncodingException;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCentorControllerTest {

	private MockMvc mvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testmvc() {

		try {
			String expectedResult = "hello world!";
			String uri = "/auth/code/publickey";
			MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders
					.get(uri).header("uid", "ss")
					.header("timestamp", System.currentTimeMillis())
					.accept(MediaType.APPLICATION_JSON);
			MvcResult mvcResult = mvc.perform(mockRequestBuilder).andReturn();

			int status = mvcResult.getResponse().getStatus();

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
