package com.guandan.ladder.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

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
	private Cache<Object, Object> cache;

	@PostConstruct
	public void init() {
		cache = CacheBuilder.newBuilder()
				// 设置并发级别为cpu核心数
				.concurrencyLevel(Runtime.getRuntime().availableProcessors())
				// 初始容量
				.initialCapacity(10)
				// 最大容量
				.maximumSize(1000)
				// 写缓存后15天过期
				.expireAfterWrite(15, TimeUnit.DAYS)
				.build();
		cache.put("1","1");//构造一个用户
	}

	public void setToken(String userName, String token) {
		cache.put(userName, token);
	}

	public String getToken(String userName) {
		return (String) cache.getIfPresent(userName);
	}
}
