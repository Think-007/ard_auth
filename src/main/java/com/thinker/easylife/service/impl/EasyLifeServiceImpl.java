package com.thinker.easylife.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.thinker.auth.util.Redis;
import com.thinker.easylife.dao.EasyLifeMapper;
import com.thinker.easylife.domain.EasyLife;
import com.thinker.easylife.service.EasyLifeService;
import com.thinker.util.ArdLog;
import com.thinker.util.JsonUtils;

@Service
public class EasyLifeServiceImpl implements EasyLifeService {
	private static final Logger logger = LoggerFactory
			.getLogger(EasyLifeServiceImpl.class);

	@Autowired
	private EasyLifeMapper easyLifeMapper;
	@Autowired
	private RestTemplate restTemplate;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	public List<EasyLife> getAllEasyLife() {
		ArdLog.info(logger, "getAllEasyLife", null, null);

		// 1.先去redis查询

		// 2.如果redis没有再去数据库查询

		List<EasyLife> easyLifeList = easyLifeMapper.queryEasyLifeList();
		ArdLog.info(logger, "getAllEasyLife", null, easyLifeList);
		return easyLifeList;
	}

	@Override
	public String getWeather(String city) {

		ArdLog.info(logger, "getWeather", null, city);

		// 1.查看本地缓存是否有数据
		Date date = Calendar.getInstance().getTime();
		String today = sdf.format(date);

		String weather = null;

		weather = (String) Redis.redis.get(city + today);

		if (weather != null) {
			return weather;
		}

		// 2.缓存没有数据就请求第三方api
		String weatherApiUrl = "https://www.sojson.com/open/api/weather/json.shtml?city=";
		weather = restTemplate.getForObject(weatherApiUrl + city, String.class);
		Map<String, String> weatherMap = JsonUtils.fromJson(weather,
				HashMap.class);
		// 3.结果不成功返回null
		if (!weatherMap.get("message").equals("Success !")) {
			weather = null;
		} else {
			// redis缓存天气信息3小时
			Redis.redis.put(city + weatherMap.get("date"), weather);
		}
		return weather;
	}

}
