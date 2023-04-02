package com.guandan.ladder.controller;

import com.hccake.ballcat.common.model.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanjiajia
 * @version 1.0
 * @date 2023/4/2
 */
@RequestMapping("/error")
@RestController
public class LoginExceptionController {

	@GetMapping("/loginException")
	public R<Void> loginException() {
		return R.failed(1000, "登陆异常，请重新登陆");
	}
}
