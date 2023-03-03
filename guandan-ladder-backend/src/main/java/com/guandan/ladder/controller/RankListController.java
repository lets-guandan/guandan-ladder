package com.guandan.ladder.controller;

import cn.hutool.core.collection.CollUtil;
import com.guandan.ladder.model.convert.UserConverter;
import com.guandan.ladder.model.entity.User;
import com.guandan.ladder.model.entity.UserGameInfo;
import com.guandan.ladder.model.vo.UserRankVO;
import com.guandan.ladder.service.UserService;
import com.guandan.ladder.service.UserGameInfoService;
import com.hccake.ballcat.common.model.result.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hccake
 */
@RequestMapping("/rank-list")
@RestController
@RequiredArgsConstructor
public class RankListController {

	private final UserGameInfoService userGameInfoService;

	private final UserService userService;

	@GetMapping
	public R<List<UserRankVO>> rank() {
		List<UserGameInfo> userGameInfoList = userGameInfoService.listByWinNumDesc();
		if (CollUtil.isEmpty(userGameInfoList)) {
			return R.ok();
		}

		List<String> uidList = userGameInfoList.stream().map(UserGameInfo::getUid).collect(Collectors.toList());
		Map<String, User> userMap = userService.listUserMapByUids(uidList);

		List<UserRankVO> list = new ArrayList<>();
		for (UserGameInfo userGameInfo : userGameInfoList) {
			UserRankVO userRankVO = new UserRankVO();
			userRankVO.setUid(userGameInfo.getUid());
			userRankVO.setWinNum(userGameInfo.getWinNum());
			userRankVO.setTotalNum(userGameInfo.getTotalNum());
			UserConverter.INSTANCE.fillUserRankVo(userRankVO, userMap.get(userGameInfo.getUid()));
			list.add(userRankVO);
		}

		return R.ok(list);
	}

}
