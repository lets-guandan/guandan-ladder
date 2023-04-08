package com.guandan.ladder.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hccake
 */
@Data
public class UserRankVO {

	/**
	 * 唯一标识
	 */
	private String uid;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

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

	/**
	 * 获胜局数
	 */
	private Integer winNum;

	/**
	 * 参与局数
	 */
	private Integer totalNum;

	/**
	 * 胜率
	 */
	private BigDecimal winPercent;

}
