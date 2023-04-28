package com.guandan.ladder.controller;

import cn.hutool.core.lang.Assert;
import com.guandan.ladder.model.dto.ConfirmRecordDto;
import com.guandan.ladder.model.dto.GameRecordDto;
import com.guandan.ladder.model.dto.GameRecordOutDto;
import com.guandan.ladder.model.dto.GameRecordUnConfirmOutDto;
import com.guandan.ladder.service.GameService;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hccake
 */
@Slf4j
@RequestMapping("/game")
@RestController
@RequiredArgsConstructor
public class GameController {

	private final GameService gameService;

	/**
	 * 上报游戏记录
	 */
	@PostMapping("/record")
	public R<Void> reportGameRecord(@RequestBody @Validated GameRecordDto gameRecordDto) {
		Set<String> uids = Stream
			.of(gameRecordDto.getWinUid1(), gameRecordDto.getWinUid2(), gameRecordDto.getLoseUid1(),
					gameRecordDto.getLoseUid2())
			.collect(Collectors.toSet());
		Assert.isTrue(uids.size() == 4, "比赛记录的成员必须是 4 人： {}", uids);
		gameService.saveRecord(gameRecordDto);
		return R.ok();
	}

	/**
	 * @param uid 用户id
	 * 历史战绩
	 */
	@GetMapping("/list")
	public R<List<GameRecordOutDto>> gameList(@RequestParam("uid") String uid) {
		List<GameRecordOutDto> gameRecordOutDtoList = gameService.gameList(uid);
		return R.ok(gameRecordOutDtoList);
	}

	/**
	 * 待确认战绩列表
	 */
	@PostMapping("/umConfirmList")
	public R<List<GameRecordUnConfirmOutDto>> umConfirmList() {
		List<GameRecordUnConfirmOutDto> unConfirmOutDtos = gameService.umConfirmList();
		return R.ok(unConfirmOutDtos);
	}

	/**
	 * 战绩确认
	 */
	@PostMapping("/confirmRecord")
	public R<Void> confirmRecord(@RequestBody @Validated ConfirmRecordDto confirmRecordDto) {
		gameService.confirmRecord(confirmRecordDto);
		return R.ok();
	}

}
