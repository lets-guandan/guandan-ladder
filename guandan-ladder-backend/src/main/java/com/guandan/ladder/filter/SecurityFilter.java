package com.guandan.ladder.filter;

import com.guandan.ladder.security.SecurityContext;
import com.guandan.ladder.util.JwtUtils;
import com.hccake.ballcat.common.model.result.R;
import com.hccake.ballcat.common.util.JsonUtils;
import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hccake
 */
public class SecurityFilter extends OncePerRequestFilter {

	private static final String HEADER_TOKEN = "token";

	private static final Set<String> IGNORE_PATH_SET = Stream.of("/", "/login").collect(Collectors.toSet());

	@Override
	protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
			FilterChain chain) throws ServletException, IOException {
		String requestUri = servletRequest.getServletPath();

		if (IGNORE_PATH_SET.contains(requestUri)) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}

		// 非登录接口需要判断token
		String token = servletRequest.getHeader(HEADER_TOKEN);
		if (StringUtils.isBlank(token)) {
			responseError(servletResponse);
			return;
		}

		// 解析 token
		JWTClaimsSet jwtClaimsSet;
		try {
			jwtClaimsSet = JwtUtils.parseToken(token);
		}
		catch (ParseException e) {
			responseError(servletResponse);
			return;
		}

		// 校验token是否过期
		if (JwtUtils.isTokenExpired(jwtClaimsSet)) {
			responseError(servletResponse);
			return;
		}

		try {
			SecurityContext.setContext(jwtClaimsSet);
			chain.doFilter(servletRequest, servletResponse);
		}
		finally {
			// 清除本地线程 避免线程复用带来错误
			SecurityContext.clear();
		}
	}

	private static void responseError(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
		R<Void> failedResult = R.failed(1000, "未获取到登录信息，请登录");
		response.getWriter().write(JsonUtils.toJson(failedResult));
	}

}
