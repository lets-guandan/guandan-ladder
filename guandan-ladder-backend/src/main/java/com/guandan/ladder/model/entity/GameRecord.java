package com.guandan.ladder.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 比赛记录表
 *
 * @author hccake 2023-03-01 20:30:13
 */
@Data
public class GameRecord {

	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 胜者1
	 */
	private String winUid1;

	/**
	 * 胜者2
	 */
	private String winUid2;

	/**
	 * 败者1
	 */
	private String loseUid1;

	/**
	 * 败者2
	 */
	private String loseUid2;

	/**
	 * 用户确认标记位，0b1111, 表示四位全部确认
	 */
	private Integer userConfirmFlagBits;

	/**
	 * 已统计到用户战绩表
	 */
	private Integer counted;

	/**
	 * 比赛时间
	 */
	private LocalDateTime gameTime;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createdTime;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updatedTime;

}