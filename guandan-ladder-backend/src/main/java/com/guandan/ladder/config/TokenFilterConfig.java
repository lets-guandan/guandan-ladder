package com.guandan.ladder.config;

import cn.hutool.core.codec.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yuanjiajia
 * @version 1.0
 * @date 2023/4/3
 */
@Configuration
public class TokenFilterConfig {

	private static final String TOKEN = "token";
	private static final String LOGIN_PATH = "/login";
	private static final String LOGIN_EXCEPTION_PATH = "/error/loginException";
	private static final String NONE_PATH = "/";
	@Resource
	private TokenMap tokenMap;

	@Bean
	public FilterRegistrationBean<OncePerRequestFilter> tokenCheckFiler() {
		FilterRegistrationBean<OncePerRequestFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(createCheckTokenFilter());
		registration.addUrlPatterns("/*");
		registration.setName("tokenCheckFiler");
		registration.setOrder(0);
		return registration;
	}

	private OncePerRequestFilter createCheckTokenFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
					throws ServletException, IOException {
				String requestUri = req.getServletPath();
				if (!LOGIN_PATH.equals(requestUri) && !NONE_PATH.equals(requestUri)) {
					// 非登录接口需要判断token
					if (!checkToken(req.getHeader(TOKEN))) {
						RequestDispatcher rd = req.getRequestDispatcher(LOGIN_EXCEPTION_PATH);
						rd.forward(req, res);
						return;
					}
				}
				chain.doFilter(req, res);
			}
		};
	}

	private Boolean checkToken(String token) {
		if (StringUtils.isNoneBlank(token)) {
			// 有token则需要校验是否合法 token：【userName-UUID】
			String decodeStr = Base64.decodeStr(token);
			String[] split = decodeStr.split("-");
			String clientToken = tokenMap.getToken(split[0]);
			return StringUtils.isNoneBlank(clientToken) && clientToken.equals(split[1]);
		}
		return false;
	}
}
