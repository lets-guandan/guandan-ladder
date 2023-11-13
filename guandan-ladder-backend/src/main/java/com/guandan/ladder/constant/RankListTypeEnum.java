package com.guandan.ladder.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hccake
 */
@Getter
@RequiredArgsConstructor
public enum RankListTypeEnum {

	WIN_COUNT(1, "胜场榜"),

	WIN_RATE(2, "胜率榜"),

	WIN_TEAM_COUNT(3, "组队胜场榜"),

	WIN_TEAM_RATE(4, "组队胜率榜"),

	;

	private final Integer value;

	private final String desc;

	public static RankListTypeEnum valueOf(Integer value) {
		for (RankListTypeEnum rankListTypeEnum : RankListTypeEnum.values()) {
			if (rankListTypeEnum.value.equals(value)) {
				return rankListTypeEnum;
			}
		}
		return null;
	}

}
