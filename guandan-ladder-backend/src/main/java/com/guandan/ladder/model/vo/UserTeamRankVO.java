package com.guandan.ladder.model.vo;

import com.guandan.ladder.model.entity.User;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xiemingzhi
 */
@Data
public class UserTeamRankVO {

	/**
	 * 用户1
	 */
	private User user1;

	/**
	 * 用户2
	 */
	private User user2;

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

	public BigDecimal getWinPercent() {
		return new BigDecimal(winNum).divide(new BigDecimal(totalNum), 2, RoundingMode.HALF_UP);
	}

	public void incrWinNum() {
		if (winNum == null) {
			winNum = 0;
		}
		winNum++;
	}

	public void incrTotalNum() {
		if (totalNum == null) {
			totalNum = 0;
		}
		totalNum++;
	}
}
