package com.thinker.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

public class RestUtils {

	public static String getRequestParameter(HttpServletRequest request,
			HttpServletResponse response) {

		if (null == request) {
			return null;
		}

		String method = request.getMethod();
		String param = null;
		if (method.equalsIgnoreCase("GET")) {
			/**
			 * 获取?后面的字符串 http://127.0.0.1:8080/test?username=zhangsan&age=100
			 * -->username=zhangsan&age=100
			 * http://127.0.0.1:8080/test?{"username":"zhangsan"}
			 * -->{"username":"zhangsan"}是json字符串 有了json串就可以映射成对象了
			 */
			param = request.getQueryString();
			if (Base64.isBase64(param)) {
				param = new String(Base64.decodeBase64(param),
						StandardCharsets.UTF_8);
			}
			System.out.println("param:" + param);
		} else {
			param = getBodyData(request);
			if (Base64.isBase64(param)) {
				param = new String(Base64.decodeBase64(param),
						StandardCharsets.UTF_8);
			}
			System.out.println("param:" + param);
		}
		return param;
	}

	// 获取请求体中的字符串(POST)
	private static String getBodyData(HttpServletRequest request) {
		StringBuffer data = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		try {
			reader = request.getReader();
			while (null != (line = reader.readLine()))
				data.append(line);
		} catch (IOException e) {
		} finally {
		}
		return data.toString();
	}
}