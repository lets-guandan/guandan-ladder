package com.guandan.ladder.model.dto;

import java.io.Serializable;

/**
 * @author yuanjiajia
 * @version 1.0
 * @date 2023/4/2
 */
public class LoginDTO implements Serializable {

	private String userName;

	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginDTO{" + "userName='" + userName + '\'' + ", password='" + password + '\'' + '}';
	}

}
