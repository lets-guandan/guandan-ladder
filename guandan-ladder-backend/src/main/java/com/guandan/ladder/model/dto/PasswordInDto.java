package com.guandan.ladder.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改密码入参对象
 *
 * @author hushaoming
 */
@Data
public class PasswordInDto {

	@NotEmpty(message = "旧密码不能为空")
	private String oldPassword;

	@NotEmpty(message = "新密码不能为空")
	private String newPassword;

}
