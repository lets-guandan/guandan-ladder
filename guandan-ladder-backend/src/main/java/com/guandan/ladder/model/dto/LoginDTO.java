package com.guandan.ladder.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author yuanjiajia 2023/4/2
 */
@Data
public class LoginDTO implements Serializable {

	@NotEmpty(message = "用户名不能为空")
	private String userName;

	@NotEmpty(message = "密码不能为空")
	private String password;

}
