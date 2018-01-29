package com.thinker.easylife.controller;

import com.thinker.creator.domain.ProcessResult;

/**
 * 便民生活模块
 * 
 * @author lipengfeia
 *
 */
public class EasyLifeController {

	public ProcessResult reqWeather() {

		ProcessResult processResult = new ProcessResult();

		processResult.setRetCode(ProcessResult.SUCCESS);

		processResult.setRetObj("weather good");
		return processResult;

	}

}
