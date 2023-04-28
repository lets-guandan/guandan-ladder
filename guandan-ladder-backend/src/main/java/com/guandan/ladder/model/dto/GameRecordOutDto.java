package com.guandan.ladder.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 历史战绩出参
 *
 * @author hushaoming
 */
@Data
public class GameRecordOutDto {

	/**
	 * 对局主键ID
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
	 * 比赛时间 建议记录比赛持续时间 和 比赛备注
	 */
	private LocalDateTime gameTime;

}
