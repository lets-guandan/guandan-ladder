package com.guandan.ladder.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author hccake 2023-03-01 17:54:07
 */
@Data
public class User {

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 用户id
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
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createdTime;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updatedTime;

}