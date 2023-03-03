package com.guandan.ladder.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author hccake
 */
@Data
public class GameRecordDto {

	/**
	 * 胜者1
	 */
	@NotEmpty(message = "胜者2不能为空")
	private String winUid1;

	/**
	 * 胜者2
	 */
	@NotEmpty(message = "胜者2不能为空")
	private String winUid2;

	/**
	 * 败者1
	 */
	@NotEmpty(message = "败者1不能为空")
	private String loseUid1;

	/**
	 * 败者2
	 */
	@NotEmpty(message = "败者2不能为空")
	private String loseUid2;

	/**
	 * 比赛时间
	 */
	@NotNull(message = "比赛时间不能为空")
	private LocalDateTime gameTime;

}
