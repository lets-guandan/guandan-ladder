package com.guandan.ladder.config;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuanjiajia
 * @version 1.0
 * @date 2023/4/2 19:26
 */
@Component
public class TokenMap implements Serializable {

	/**
	 * 缓存所有的登录用户token
	 */
	private final ConcurrentHashMap<String, String> tokens = new ConcurrentHashMap<>(16);

	public void setToken(String userName, String token) {
		tokens.put(userName, token);
	}

	public String getToken(String userName) {
		return tokens.get(userName);
	}
}
