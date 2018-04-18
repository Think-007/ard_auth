package com.thinker.auth.controller;

import javax.annotation.Resource;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class TestController {

	@Resource
	RestTemplate r;

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

	@RequestMapping("/weather")
	public String weather() {
		String weatherApiUrl = "https://www.sojson.com/open/api/weather/json.shtml?city=";
		String s = r.getForObject(weatherApiUrl, String.class);
		System.out.println(s);

		return s;

	}
}
