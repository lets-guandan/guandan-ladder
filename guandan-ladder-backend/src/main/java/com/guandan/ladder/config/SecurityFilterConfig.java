package com.guandan.ladder.config;

import com.guandan.ladder.filter.SecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author yuanjiajia 2023/4/3
 */
@Configuration
public class SecurityFilterConfig {

	@Bean
	public FilterRegistrationBean<OncePerRequestFilter> securityFilter() {
		FilterRegistrationBean<OncePerRequestFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new SecurityFilter());
		registration.addUrlPatterns("/*");
		registration.setName("securityFilter");
		registration.setOrder(0);
		return registration;
	}

}
