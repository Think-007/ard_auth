package com.thinker.easylife.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinker.auth.domain.ArdType;
import com.thinker.easylife.domain.EasyLife;

public interface EasyLifeMapper {

	/**
	 * 查询所有的便民服务地址
	 * 
	 * @return
	 */
	public List<EasyLife> queryEasyLifeList();

	/**
	 * 根据业务号查询相应业务的类型列表
	 * 
	 * @param id
	 * @return
	 */
	public List<ArdType> queryTypeByBizId(@Param("id") int id);

}
