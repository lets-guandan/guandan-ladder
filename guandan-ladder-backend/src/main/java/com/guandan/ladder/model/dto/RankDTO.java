package com.guandan.ladder.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author hushaoming 2023/4/8
 */
@Data
public class RankDTO {

	/**
	 * 胜场:winNum 胜率:winPercent
	 */
	private String tag;

}
