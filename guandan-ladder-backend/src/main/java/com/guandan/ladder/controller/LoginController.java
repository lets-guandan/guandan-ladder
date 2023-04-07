package com.guandan.ladder.controller;

import cn.hutool.core.lang.Assert;
import com.guandan.ladder.model.dto.LoginDTO;
import com.guandan.ladder.model.entity.User;
import com.guandan.ladder.service.UserService;
import com.guandan.ladder.util.JwtUtils;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author yuanjiajia 2023/4/2
 */
@Slf4j
@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

	private final UserService userService;

	@PostMapping
	public R<String> login(@Validated @RequestBody LoginDTO loginDTO) {
		String userName = loginDTO.getUserName();
		log.info("user login, name:{}", userName);

		User user = userService.queryByName(userName.trim());
		Assert.notNull(user, "用户名或密码错误");

		// TODO: 用户密码应该密文存储
		if (user.getPassword().equals(loginDTO.getPassword().trim())) {
			String token = JwtUtils.createToken(user.getUid(), new HashMap<>());
			log.info("user login success, name:{}", userName);
			return R.ok(token);
		}
		else {
			log.warn("user login failed, name:{}", userName);
			return R.failed(500, "用户名或密码错误");
		}
	}

}
