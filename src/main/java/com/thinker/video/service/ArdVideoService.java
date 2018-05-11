package com.thinker.video.service;

import java.util.List;

import com.thinker.video.domain.ArdAdvertiseMent;
import com.thinker.video.domain.ArdVideo;

public interface ArdVideoService {

	/**
	 * 查询所有的video列表
	 * 
	 * @return
	 */
	public List<ArdVideo> getAllVideoList();

	/**
	 * 查询所有广告
	 * 
	 * @return
	 */
	public List<ArdAdvertiseMent> queryArdAdvList();
}
