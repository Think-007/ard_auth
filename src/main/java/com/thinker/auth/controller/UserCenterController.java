package com.thinker.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.think.creator.domain.ProcessResult;

@Controller
@RequestMapping("/usercenter")
public class UserCenterController {

	
	//签到
	@RequestMapping("/singnin")
	public ProcessResult singIn() {

		return null;

	}
	
	@RequestMapping("/signout_req/{uid}")
	public void checkOut(String uid) {

		// app还要删除token

	}

}
