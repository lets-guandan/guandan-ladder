package com.guandan.ladder.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 待确认列表出参
 *
 * @author hushaoming
 */
@Data
public class GameRecordVO {

	/**
	 * 对局主键ID
	 */
	private Integer id;

	/**
	 * 胜者1 Uid
	 */
	private String winUid1;
	//确认状态
	private char winUid1Flag;

	/**
	 * 胜者1 昵称
	 */
	private String winNickname1;

	/**
	 * 胜者2 Uid
	 */
	private String winUid2;
	//确认状态
	private char winUid2Flag;

	/**
	 * 胜者2 昵称
	 */
	private String winNickname2;

	/**
	 * 败者1
	 */
	private String loseUid1;
	//确认状态
	private char loseUid1Flag;

	/**
	 * 败者1 昵称
	 */
	private String loseNickname1;

	/**
	 * 败者2
	 */
	private String loseUid2;
	//确认状态
	private char loseUid2Flag;

	/**
	 * 败者2 昵称
	 */
	private String loseNickname2;

	/**
	 * 比赛时间
	 */
	private LocalDateTime gameTime;

	/**
	 * 确认状态 示例：1000
	 */
	private String userConfirmFlag;

	/**
	 * 确认状态十进制 示例：7
	 */
	private int userConfirmFlagBits;

}
