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

import com.think.creator.domain.ProcessResult;
import com.thinker.auth.util.Redis;

/**
 * 拦截token请求
 * 
 * @author lipengfeia
 *
 */
@WebFilter(filterName = "usercenter", urlPatterns = "/filter/*")
public class UserInfoFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;

		System.out.println("进入token拦截器");

		String token = request.getHeader("token");

		ProcessResult processResult = new ProcessResult();
		processResult.setRetMsg("发发发");

		if (Redis.redis.get(token) == null) {

			System.out.println("请求被拦截");

			request.setAttribute("processResult", processResult);

			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("/auth/gate/tokenstatus");
			requestDispatcher.forward(arg0, arg1);

		} else {

			arg2.doFilter(arg0, arg1);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

		System.out.println("filter 初始化");
	}

}
