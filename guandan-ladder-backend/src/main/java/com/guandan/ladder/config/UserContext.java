package com.guandan.ladder.config;

/**
 * 使用本地线程存放登录用户信息 来源 chatgpt
 *
 * @author hushaoming
 */
public class UserContext {

	private static final ThreadLocal<String> userIdThreadLocal = new ThreadLocal<>();

	public static void setUserId(String userId) {
		userIdThreadLocal.set(userId);
	}

	public static String getUserId() {
		return userIdThreadLocal.get();
	}

	public static void clear() {
		userIdThreadLocal.remove();
	}

}