package com.guandan.ladder.controller;

import com.guandan.ladder.model.dto.RankDTO;
import com.guandan.ladder.model.vo.UserRankVO;
import com.guandan.ladder.service.UserGameInfoService;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
	@PostMapping("/list")
	public R<List<UserRankVO>> rank(@RequestBody RankDTO rankDTO) {
		List<UserRankVO> userRankVOS = userGameInfoService.rank(rankDTO);
		return R.ok(userRankVOS);
	}

}
