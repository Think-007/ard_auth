package com.thinker.easylife.service;

import java.util.List;

import com.thinker.auth.domain.ArdType;
import com.thinker.easylife.domain.EasyLife;

public interface EasyLifeService {

	/**
	 * 查询所有的便民的地址
	 * 
	 * @return
	 */
	public List<EasyLife> getAllEasyLife();

	/**
	 * 查询天气信息
	 * 
	 * @param city
	 * @return
	 */
	public String getWeather(String city);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<ArdType> getAllTypeByBizID(int id);
}
