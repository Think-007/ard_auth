package com.thinker.video.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinker.creator.domain.ProcessResult;
import com.thinker.util.ArdConst;
import com.thinker.video.domain.ArdVideo;

@RestController
public class RadioController {

	@RequestMapping(value = "/video_list")
	public ProcessResult reqVideoList() {

		ProcessResult processResult = new ProcessResult();

		List list = new ArrayList<ArdVideo>();

		for (int i = 0; i < 10; i++) {
			ArdVideo video = new ArdVideo();
			video.setAddress("北京");
			video.setName("阿尔丁电台" + (int) (Math.random() * 100));
			video.setType("type");
			video.setVideoUrl("www.baidu.com");

			list.add(video);

		}

		processResult.setRetCode(ProcessResult.SUCCESS);
		processResult.setRetObj(list);

		return processResult;
	}
}
