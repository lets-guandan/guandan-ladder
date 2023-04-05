package com.guandan.ladder.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 待确认列表出参
 * @author hushaoming
 */
@Data
public class GameRecordUnConfirmOutDto {

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
	 * 比赛时间
	 */
	private LocalDateTime gameTime;

	/**
	 * 确认状态
	 * 示例：1000
	 */
	private String userConfirmFlag;
	/**
	 * 确认状态十进制
	 * 示例：7
	 */
	private int userConfirmFlagBits;

}
