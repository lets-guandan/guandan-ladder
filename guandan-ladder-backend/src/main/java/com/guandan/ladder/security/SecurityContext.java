package com.guandan.ladder.security;

import com.nimbusds.jwt.JWTClaimsSet;

import java.util.Optional;

/**
 * 使用本地线程存放登录用户信息 来源 chatgpt
 *
 * @author hushaoming
 */
public final class SecurityContext {

	private SecurityContext() {
	}

	private static final ThreadLocal<JWTClaimsSet> CONTEXT = new ThreadLocal<>();

	public static void setContext(JWTClaimsSet claimsSet) {
		CONTEXT.set(claimsSet);
	}

	public static String getUserId() {
		return Optional.ofNullable(CONTEXT.get()).map(JWTClaimsSet::getSubject).orElse(null);
	}

	public static void clear() {
		CONTEXT.remove();
	}

}