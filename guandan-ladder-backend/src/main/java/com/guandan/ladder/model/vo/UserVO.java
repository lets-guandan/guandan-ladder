package com.guandan.ladder.model.vo;

import lombok.Data;

/**
 * @author hccake
 */
@Data
public class UserVO {

	/**
	 * 用户id
	 */
	private String uid;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 个人简介
	 */
	private String profile;

	/**
	 * 头像url
	 */
	private String avatarUrl;

}
