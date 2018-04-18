package com.thinker.easylife.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thinker.auth.domain.ArdType;
import com.thinker.auth.domain.result.EasyLifeResult;
import com.thinker.auth.util.Redis;
import com.thinker.creator.domain.ProcessResult;
import com.thinker.easylife.domain.EasyLife;
import com.thinker.easylife.service.EasyLifeService;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;
import com.thinker.util.LimitQueue;

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
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			processResult.setRetObj(t);
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
	@RequestMapping(value = "/easy_life_list", method = RequestMethod.GET)
	public ProcessResult reqEasyLifeList(HttpServletRequest request) {

		ArdLog.debug(logger, "enter reqEasyLifeList", null, null);
		ProcessResult processResult = new ProcessResult();

		try {
			String uid = request.getHeader("uid");
			// 1.查询便民列表
			List<EasyLife> easyList = easyLifeService.getAllEasyLife();

			// 2.查询类型列表
			List<ArdType> typeList = easyLifeService.getAllTypeByBizID(0);

			// 3.获取最近使用列表
			@SuppressWarnings("unchecked")
			LimitQueue<EasyLife> queue = (LimitQueue<EasyLife>) Redis.redis
					.get(uid + "_recent");
			List<EasyLife> recentList = null;
			if (queue != null) {
				for (int i = 0; i < queue.size(); i++) {
					EasyLife temp = (EasyLife) queue.get(i);
					temp.setType(0 + "");
				}
				recentList = queue.getAllList();
			}

			EasyLifeResult result = new EasyLifeResult();

			result.setEasyLifeList(easyList);
			result.setTypeList(typeList);
			result.setRecentEasyLifeList(recentList);

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetObj(result);
		} catch (Throwable t) {
			t.printStackTrace();
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			processResult.setRetObj(t);
		}
		ArdLog.debug(logger, "finish reqEasyLifeList", null,
				" processResult : " + processResult);
		return processResult;

	}

	@RequestMapping(value = "/touch", method = RequestMethod.POST)
	public ProcessResult touchItem(HttpServletRequest request, EasyLife easyLife) {
		ArdLog.debug(logger, "enter touchItem", null, " easyLife : " + easyLife);
		ProcessResult processResult = new ProcessResult();
		try {
			String uid = request.getHeader("uid");

			LimitQueue<EasyLife> recentList = addRecent(easyLife, uid);

			Redis.redis.put(uid + "_recent", recentList);
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
		} catch (Throwable t) {
			t.printStackTrace();
			processResult.setRetCode(ArdError.EXCEPTION);
			processResult.setRetMsg(ArdError.EXCEPTION_MSG);
			processResult.setRetObj(t);
		}
		ArdLog.debug(logger, "finish touchItem", null, " processResult : "
				+ processResult);
		return processResult;

	}

	private LimitQueue<EasyLife> addRecent(EasyLife easyLife, String uid) {
		@SuppressWarnings("unchecked")
		LimitQueue<EasyLife> recentList = (LimitQueue<EasyLife>) Redis.redis
				.get(uid + "_recent");
		if (recentList == null) {
			recentList = new LimitQueue<EasyLife>(4);
		}

		ArrayList<EasyLife> list = (ArrayList<EasyLife>) recentList
				.getAllList();

		Iterator<EasyLife> it = list.iterator();
		while (it.hasNext()) {
			EasyLife item = it.next();
			if (item.getName().equals(easyLife.getName())) {
				it.remove();
			}
		}

		recentList.offer(easyLife);
		return recentList;
	}
}
