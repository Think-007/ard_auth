package com.thinker.filter;

import java.io.IOException;

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

import com.thinker.creator.domain.ProcessResult;
import com.thinker.util.ArdError;
import com.thinker.util.ArdLog;

//@WebFilter(filterName = "urlfilter", urlPatterns = "/*")
public class URLFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(URLFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ArdLog.info(logger, "init", null, "url filter init");
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;

		ArdLog.debug(logger, "doFilter", null, "enter url interceptor");
		ProcessResult processResult = new ProcessResult();
		String timeStamp = request.getParameter("timeStamp");

		if (System.currentTimeMillis() - Long.parseLong(timeStamp) > 60 * 1000) {

			ArdLog.debug(logger, "doFilter", null, "url is time out");

			processResult.setRetCode(ArdError.URL_TIME_OUT);
			processResult.setRetMsg("url 过期");
			request.setAttribute("processResult", processResult);

			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("/auth/gate/tokenstatus");
			requestDispatcher.forward(arg0, arg1);
		} else {
			processResult.setRetCode(ProcessResult.SUCCESS);
			processResult.setRetMsg("ok");
			arg2.doFilter(arg0, arg1);
		}
		ArdLog.debug(logger, "doFilter", null, "finish url interceptor");

	}

	@Override
	public void destroy() {

		ArdLog.info(logger, "destroy", null, "url filter destroy");

	}

}
