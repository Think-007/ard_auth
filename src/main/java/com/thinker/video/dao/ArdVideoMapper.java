package com.thinker.video.dao;

import java.util.List;

import com.thinker.video.domain.ArdAdvertiseMent;
import com.thinker.video.domain.ArdVideo;

public interface ArdVideoMapper {

	/**
	 * 查询所有的video地址列表
	 * 
	 * @return
	 */
	public List<ArdVideo> queryAllVideoList();

	/**
	 * 查询滚动广告
	 * 
	 * @return
	 */
	public List<ArdAdvertiseMent> listArdAdv();

}
