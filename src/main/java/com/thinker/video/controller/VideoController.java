package com.thinker.video.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thinker.auth.domain.ArdType;
import com.thinker.auth.domain.result.VideoResult;
import com.thinker.creator.domain.ProcessResult;
import com.thinker.easylife.service.EasyLifeService;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;
import com.thinker.video.domain.ArdVideo;
import com.thinker.video.service.ArdVideoService;

@RestController
@RequestMapping("/video")
public class VideoController {

	private static final Logger logger = LoggerFactory
			.getLogger(VideoController.class);

	@Autowired
	private ArdVideoService ardVideoService;

	@Autowired
	private EasyLifeService easyLifeService;

	@RequestMapping(value = "/video_list")
	public ProcessResult reqVideoList() {

		ArdLog.info(logger, "reqVideoList", null, null);

		ProcessResult processResult = new ProcessResult();

		try {
			List<ArdVideo> videoList = new ArrayList<ArdVideo>();

			/*
			 * for (int i = 0; i < 10; i++) { ArdVideo video = new ArdVideo();
			 * video.setAddress("北京"); video.setName("阿尔丁电台" + (int)
			 * (Math.random() * 100)); video.setType("type");
			 * video.setVideoUrl("www.baidu.com");
			 * 
			 * list.add(video);
			 * 
			 * }
			 */

			videoList = ardVideoService.getAllVideoList();

			List<ArdType> typeList = easyLifeService.getAllTypeByBizID(1);
			VideoResult result = new VideoResult();
			result.setVideoList(videoList);
			result.setTypeList(typeList);

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetObj(result);
		} catch (Throwable t) {
			t.printStackTrace();
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			processResult.setRetObj(t);
			ArdLog.error(logger, "reqVideoList", null, processResult, t);
		}
		ArdLog.info(logger, "reqVideoList", null, processResult);
		return processResult;
	}
}
