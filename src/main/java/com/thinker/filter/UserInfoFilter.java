package com.thinker.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinker.auth.util.Redis;
import com.thinker.creator.domain.ProcessResult;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;
import com.thinker.util.MD5Util;

/**
 * 拦截token请求
 * 
 * @author lipengfeia
 *
 */
// @WebFilter(filterName = "usercenter", urlPatterns = "/**/filter/*")
public class UserInfoFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(UserInfoFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		ArdLog.info(logger, "destroy", null, "token filter 结束");

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;

		System.out.println("进入token拦截器");
		ProcessResult processResult = new ProcessResult();
		String uid = request.getHeader("uid");
		String reqToken = request.getHeader("token");
		String token = (String) Redis.redis.get(uid);

		if (token == null || reqToken == null || !reqToken.equals(token)) {

			System.out.println("token 不存在");

			processResult.setRetCode(ArdError.TOKEN_TIME_OUT);
			processResult.setRetMsg("token 过期");

			request.setAttribute("processResult", processResult);

			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("/auth/gate/tokenstatus");
			requestDispatcher.forward(arg0, arg1);

		} else if (!authSign(request, token)) {

			System.out.println("签名不合法");

			processResult.setRetCode(ArdError.PARAM_ILLEGAL);
			processResult.setRetMsg("签名不合法");

			request.setAttribute("processResult", processResult);

			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("/auth/gate/tokenstatus");
			requestDispatcher.forward(arg0, arg1);

		} else {

			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			arg2.doFilter(arg0, arg1);

		}

	}

	/**
	 * 校验请求参数
	 * 
	 * @param request
	 * @param token
	 */
	private boolean authSign(HttpServletRequest request, String token) {
		SortedMap<String, String> paramsMap = new TreeMap<String, String>();

		Enumeration<String> paramNames = request.getParameterNames();

		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();

			if (paramName.equals("sign")) {
				continue;
			}

			System.out.println(paramName);

			String paramValues = (String) request.getParameter(paramName);

			System.out.println(paramValues);

			paramsMap.put(paramName, paramValues);

		}

		StringBuilder sb = new StringBuilder();

		Set es = paramsMap.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("token=" + token);
		System.out.println("服务端签名 : " + sb.toString());
		ArdLog.info(logger, "url filter", null, "待签名数据= : " + sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8");

		String reqSign = (String) request.getAttribute("sign");

		System.out.println("请求端签名 : " + reqSign);
		ArdLog.info(logger, "url filter", null, "请求中的签名数据= : " + reqSign);

		return sign.equalsIgnoreCase(reqSign);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

		ArdLog.info(logger, "init", null, "token filter 初始化");
	}

}
