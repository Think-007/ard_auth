package com.thinker.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.think.creator.domain.ProcessResult;
import com.thinker.auth.controller.AuthCodeController;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;

//@WebFilter(filterName = "urlfilter", urlPatterns = "/*")
public class URLFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(URLFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		ArdLog.info(logger, "init", null, "url filter 初始化");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;

		System.out.println("进入url拦截器");
		ProcessResult processResult = new ProcessResult();
		processResult.setRetCode(ProcessResult.FAILED);
		processResult.setRetMsg("failed");
		String timeStamp = request.getHeader("timeStamp");

		if (System.currentTimeMillis() - Long.parseLong(timeStamp) > 600 * 1000) {

			System.out.println("url 过期");

			processResult.setErrorCode(ArdError.URL_TIME_OUT);
			processResult.setErrorDesc("url 过期");
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

		ArdLog.info(logger, "destroy", null, "url filter 结束");

	}

}
