package com.thinker.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value = "/filter/test")
	public String test() {

		System.out.println("test");
		return "gate";
	}

	@RequestMapping(value = "/test")
	public String test1() {
		System.out.println("test1");
		return "filter";
	}

	@RequestMapping(value = "/**/filter/test")
	public String test2() {
		System.out.println("test2");
		return "filter2";
	}
	
	@RequestMapping(value = "/*/filter/test")
	public String test3() {
		System.out.println("test3");
		return "filter3";
	}
}
