package com.thinker.easylife.service;

import java.util.List;

import com.thinker.easylife.domain.EasyLife;

public interface EasyLifeService {

	/**
	 * 查询所有的便民的地址
	 * 
	 * @return
	 */
	public List<EasyLife> getAllEasyLife();

	public String getWeather(String city);
}
