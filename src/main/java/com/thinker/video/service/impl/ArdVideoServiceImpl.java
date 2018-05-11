package com.thinker.video.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinker.easylife.controller.EasyLifeController;
import com.thinker.util.ArdLog;
import com.thinker.video.dao.ArdVideoMapper;
import com.thinker.video.domain.ArdAdvertiseMent;
import com.thinker.video.domain.ArdVideo;
import com.thinker.video.service.ArdVideoService;

@Service
public class ArdVideoServiceImpl implements ArdVideoService {
	private static final Logger logger = LoggerFactory
			.getLogger(ArdVideoServiceImpl.class);
	@Autowired
	private ArdVideoMapper ardVideoMapper;

	@Override
	public List<ArdVideo> getAllVideoList() {

		ArdLog.info(logger, "getAllVideoList", null, null);

		// 1.先去redis查询
		// 2.redis没有再去数据库查询
		List<ArdVideo> videoList = ardVideoMapper.queryAllVideoList();

		ArdLog.info(logger, "getAllVideoList", null, videoList);

		return videoList;
	}

	@Override
	public List<ArdAdvertiseMent> queryArdAdvList() {

		List<ArdAdvertiseMent> advList = ardVideoMapper.listArdAdv();

		return advList;
	}

}
