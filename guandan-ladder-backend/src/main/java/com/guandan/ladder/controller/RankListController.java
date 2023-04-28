package com.guandan.ladder.controller;

import com.guandan.ladder.constant.RankListTypeEnum;
import com.guandan.ladder.model.vo.UserRankVO;
import com.guandan.ladder.service.UserGameInfoService;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hccake
 */
@RequestMapping("/rank")
@RestController
@RequiredArgsConstructor
public class RankListController {

	private final UserGameInfoService userGameInfoService;

	/**
	 * 排行榜：胜场 & 胜率
	 */
	@GetMapping("/list")
	public R<List<UserRankVO>> rank(@RequestParam("rankType") Integer rankType) {
		RankListTypeEnum rankListTypeEnum = RankListTypeEnum.valueOf(rankType);
		Assert.notNull(rankListTypeEnum, "未知的排行类型：" + rankType);
		List<UserRankVO> userRankVOS = userGameInfoService.rank(rankListTypeEnum);
		return R.ok(userRankVOS);
	}

}
