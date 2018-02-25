package com.thinker.easylife.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thinker.creator.domain.ProcessResult;
import com.thinker.easylife.domain.EasyLife;
import com.thinker.easylife.service.EasyLifeService;
import com.thinker.util.ArdLog;

/**
 * 便民生活模块
 * 
 * @author lipengfeia
 *
 */
@RestController
@RequestMapping("/easylife")
public class EasyLifeController {
	private static final Logger logger = LoggerFactory
			.getLogger(EasyLifeController.class);

	@Autowired
	private EasyLifeService easyLifeService;

	/**
	 * 天气查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/weather", method = RequestMethod.GET)
	public ProcessResult reqWeather(String city) {

		ArdLog.info(logger, "reqWeather", null, city);

		ProcessResult processResult = new ProcessResult();
		try {
			String weather = easyLifeService.getWeather(city);
			if (null != weather) {
				processResult.setRetCode(ProcessResult.SUCCESS);
				processResult.setRetObj(weather);
			}

		} catch (Throwable t) {
			t.printStackTrace();
			ArdLog.error(logger, "reqWeather", null, processResult, t);
		}
		ArdLog.info(logger, "reqWeather", null, processResult);
		return processResult;

	}

	/**
	 * 便民列表查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/easy_lif_list", method = RequestMethod.GET)
	public ProcessResult reqEasyLifeList() {

		ProcessResult processResult = new ProcessResult();

		// 1.查询便民列表
		List<EasyLife> easyList = easyLifeService.getAllEasyLife();

		processResult.setRetCode(ProcessResult.SUCCESS);
		processResult.setRetObj(easyList);
		return processResult;

	}

}
