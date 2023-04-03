package com.guandan.ladder.controller;

import cn.hutool.core.codec.Base64;
import com.guandan.ladder.config.TokenMap;
import com.guandan.ladder.model.dto.LoginDTO;
import com.guandan.ladder.model.entity.User;
import com.guandan.ladder.service.UserService;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * @author yuanjiajia
 * @version 1.0
 * @date 2023/4/2
 */
@Slf4j
@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

	private static final String MID_LINE = "-";

	private final UserService userService;

	private final TokenMap tokenMap;

	@PostMapping
	public R<String> login(@RequestBody LoginDTO loginDTO) {
		if (Objects.isNull(loginDTO)
				|| StringUtils.isBlank(loginDTO.getUserName())
				|| StringUtils.isBlank(loginDTO.getPassword())) {
			return R.failed(500, "用户名或密码不允许为空");
		}
		log.info("user login, name:{}", loginDTO.getUserName());
		User user = userService.queryByName(loginDTO.getUserName().trim());
		if (Objects.nonNull(user) && user.getPassword().equals(loginDTO.getPassword().trim())) {
			String token = UUID.randomUUID().toString().replaceAll(MID_LINE, "");
			tokenMap.setToken(loginDTO.getUserName(), token);
			String userToken = String.join(MID_LINE, user.getUsername(), token);
			String encode = Base64.encode(userToken.getBytes(StandardCharsets.UTF_8));
			return R.ok(encode);
		}
		return R.failed(500, "用户名或密码错误");
	}

}
