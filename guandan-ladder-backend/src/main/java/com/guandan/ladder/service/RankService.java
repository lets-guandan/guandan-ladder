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

	/**
	 * 定位赛次数，如果用户的总场次低于定位赛，则排名时移到末尾
	 */
	private static final int PLACEMENT_MATCH_LIMIT = 10;

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

		return getRankList(userGameInfoList, userMap);
	}

	/**
	 * 获取排行榜
	 */
	private static List<UserRankVO> getRankList(List<UserGameInfo> userGameInfoList, Map<String, User> userMap) {
		List<UserRankVO> list = new ArrayList<>();
		List<UserRankVO> placementList = new ArrayList<>();
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

			// 如果没有打完定位赛，则排名后移
			if (userRankVO.getTotalNum() >= PLACEMENT_MATCH_LIMIT) {
				list.add(userRankVO);
			}
			else {
				placementList.add(userRankVO);
			}
		}
		// 添加定位赛数据
		list.addAll(placementList);
		return list;
	}

}
