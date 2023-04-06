package com.guandan.ladder.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 确认战绩dto 采用按位或运算 1111表示全部确认 =15 0001=1 败者2 0010=2 败者1 0011=3 0100=4 胜者2 0101=5 0110=6
 * 0111=7 1000=8 胜者1 1001=9 1010=10 1011=11 1100=12 1101=13 1110=14 1111=15
 *
 * @author hushaoming
 */
@Data
public class ConfirmRecordDto {

	/**
	 * 对局id
	 */
	@NotNull(message = "对局id不能为空")
	private String recordId;

}
