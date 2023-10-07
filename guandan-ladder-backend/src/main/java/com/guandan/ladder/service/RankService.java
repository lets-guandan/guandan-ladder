package com.guandan.ladder.service;

import cn.hutool.core.collection.CollUtil;
import com.guandan.ladder.constant.RankListTypeEnum;
import com.guandan.ladder.mapper.UserGameInfoMapper;
import com.guandan.ladder.model.convert.UserConverter;
import com.guandan.ladder.model.entity.User;
import com.guandan.ladder.model.entity.UserGameInfo;
import com.guandan.ladder.model.vo.UserRankVO;
import com.hccake.ballcat.common.core.exception.BusinessException;
import com.hccake.ballcat.common.model.result.SystemResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 天梯 Service
 *
 * @author hccake
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RankService {

	private final UserService userService;

	private final UserGameInfoMapper userGameInfoMapper;

	public List<UserRankVO> rankList(RankListTypeEnum rankListTypeEnum) {
		List<UserGameInfo> userGameInfoList;

		switch (rankListTypeEnum) {
			case WIN_COUNT:
				userGameInfoList = userGameInfoMapper.listByWinNumDesc();
				break;
			case WIN_RATE:
				userGameInfoList = userGameInfoMapper.listByWinPercentDesc();
				break;
			default:
				throw new BusinessException(SystemResultCode.BAD_REQUEST.getCode(), "错误的排行类型");
		}

		if (CollUtil.isEmpty(userGameInfoList)) {
			return new ArrayList<>();
		}

		List<String> uidList = userGameInfoList.stream().map(UserGameInfo::getUid).collect(Collectors.toList());
		Map<String, User> userMap = userService.listUserMapByUids(uidList);

		List<UserRankVO> list = new ArrayList<>();
		for (UserGameInfo userGameInfo : userGameInfoList) {
			UserRankVO userRankVO = new UserRankVO();
			userRankVO.setUid(userGameInfo.getUid());
			userRankVO.setWinNum(userGameInfo.getWinNum());
			userRankVO.setTotalNum(userGameInfo.getTotalNum());
			// 计算胜率，保留2位小数
			BigDecimal divide = new BigDecimal(userGameInfo.getWinNum())
				.divide(new BigDecimal(userGameInfo.getTotalNum()), 2, RoundingMode.HALF_UP);
			userRankVO.setWinPercent(divide);
			UserConverter.INSTANCE.fillUserRankVo(userRankVO, userMap.get(userGameInfo.getUid()));
			list.add(userRankVO);
		}

		return list;
	}

}
