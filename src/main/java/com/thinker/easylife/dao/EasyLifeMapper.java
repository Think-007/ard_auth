package com.thinker.easylife.dao;

import java.util.List;

import com.thinker.easylife.domain.EasyLife;

public interface EasyLifeMapper {

	/**
	 * 查询所有的便民服务地址
	 * 
	 * @return
	 */
	public List<EasyLife> queryEasyLifeList();

}
