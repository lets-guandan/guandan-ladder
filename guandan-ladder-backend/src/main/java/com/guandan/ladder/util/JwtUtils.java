package com.guandan.ladder.util;

import com.guandan.ladder.exception.TokenException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.util.CollectionUtils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * 利用 nimbus-jose-jwt 实现的 Jwt 工具类
 *
 * @author hccake
 */
public final class JwtUtils {

	private JwtUtils() {
	}

	private static final String SECRET_KEY = "oZjv7r/vAOzE7svJ6pTxDZJwo4vCvi5YsYyZ0EaIMEI="; // 可以替换为自己的密钥

	public static String createToken(String sub, Map<String, Object> customClaims) {
		// 生成 JwsHeader
		JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();

		// 设置JWT的载荷，即需要传递的数据，比如用户ID、角色、过期时间等
		long currentTimeMillis = System.currentTimeMillis();
		JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder().subject(sub)
			.expirationTime(new Date(currentTimeMillis + 7 * 24 * 60 * 60 * 1000)) // 设置过期时间
			.issueTime(new Date(currentTimeMillis));// 设置签发时间
		if (!CollectionUtils.isEmpty(customClaims)) {
			for (Map.Entry<String, Object> entry : customClaims.entrySet()) {
				builder.claim(entry.getKey(), entry.getValue());
			}
		}
		JWTClaimsSet payload = builder.build();

		SignedJWT jwt = new SignedJWT(header, payload);

		// We need a 256-bit key for HS256 which must be pre-shared
		byte[] sharedKey = new byte[32];
		new SecureRandom().nextBytes(sharedKey);

		// 生成 JwtSignature
		MACSigner signer;
		try {
			signer = new MACSigner(SECRET_KEY);
			jwt.sign(signer);
		}
		catch (JOSEException e) {
			throw new TokenException("token 签名异常", e);
		}

		// 生成 JwtToken
		// Output in URL-safe format
		return jwt.serialize();
	}

	public static JWTClaimsSet parseToken(String token) throws ParseException {
		return SignedJWT.parse(token).getJWTClaimsSet();
	}

	public static boolean isTokenExpired(JWTClaimsSet jwtClaimsSet) {
		return jwtClaimsSet.getExpirationTime().before(new Date());
	}

}