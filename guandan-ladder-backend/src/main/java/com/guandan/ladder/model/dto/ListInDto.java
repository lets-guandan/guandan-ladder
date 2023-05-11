package com.guandan.ladder.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 历史记录入参对象
 *
 * @author hushaoming
 */
@Data
public class ListInDto {

	// 用户id
	private String uid;

	// 查询最近x天 如果这个不为空 以这个为准
	private long days;

	// 自定义 查询开始时间 默认15天 格式"2022-12-03 10:15:30"
	private LocalDateTime startTime;

	// 自定义 查询结束时间 默认当前系统时间 格式"2022-12-03 10:15:30"
	private LocalDateTime endTime;

}
