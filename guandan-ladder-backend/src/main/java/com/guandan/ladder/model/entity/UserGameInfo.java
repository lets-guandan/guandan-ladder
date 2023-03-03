package com.guandan.ladder.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户战绩
 *
 * @author hccake 2023-03-01 17:54:07
 */
@Data
public class UserGameInfo {

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 用户id
	 */
	private String uid;

	/**
	 * 获胜局数
	 */
	private Integer winNum;

	/**
	 * 参与局数
	 */
	private Integer totalNum;

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