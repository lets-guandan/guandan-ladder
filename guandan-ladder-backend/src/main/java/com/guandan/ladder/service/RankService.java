package com.guandan.ladder.service;

import cn.hutool.core.collection.CollUtil;
import com.guandan.ladder.constant.RankListTypeEnum;
import com.guandan.ladder.mapper.GameRecordMapper;
import com.guandan.ladder.mapper.UserGameInfoMapper;
import com.guandan.ladder.mapper.UserMapper;
import com.guandan.ladder.model.convert.UserConverter;
import com.guandan.ladder.model.entity.GameRecord;
import com.guandan.ladder.model.entity.User;
import com.guandan.ladder.model.entity.UserGameInfo;
import com.guandan.ladder.model.vo.UserRankVO;
import com.guandan.ladder.model.vo.UserTeamRankVO;
import com.hccake.ballcat.common.core.exception.BusinessException;
import com.hccake.ballcat.common.model.result.SystemResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
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

	/**
	 * 组队定位赛次数，如果组队总场次低于阈值，则排名时移到末尾
	 */
	private static final int PLACEMENT_MATCH_TEAM_LIMIT = 5;

	private final UserService userService;

	private final UserGameInfoMapper userGameInfoMapper;

	private final UserMapper userMapper;

	private final GameRecordMapper gameRecordMapper;

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
	 * 组队排行
	 */
	public List<UserTeamRankVO> teamRank(RankListTypeEnum rankListTypeEnum) {
		List<GameRecord> gameRecords = gameRecordMapper.selectAllValidRecords();

		Map<String, UserTeamRankVO> rankMap = new HashMap<>(16);
		for (GameRecord record : gameRecords) {
			// 统计胜者
			UserTeamRankVO winTeamRankVO = this.fillUserTeamRankVO(record.getWinUid1(), record.getWinUid2(), rankMap);
			winTeamRankVO.incrWinNum();
			winTeamRankVO.incrTotalNum();

			// 统计负者
			UserTeamRankVO loseTeamRankVO = this.fillUserTeamRankVO(record.getLoseUid1(), record.getLoseUid2(), rankMap);
			loseTeamRankVO.incrTotalNum();
		}

		// 排行
		List<UserTeamRankVO> userTeamRankVOS;
		switch (rankListTypeEnum) {
			case WIN_TEAM_COUNT:
				userTeamRankVOS = rankMap.values().stream()
						.filter(v -> PLACEMENT_MATCH_TEAM_LIMIT <= v.getTotalNum())
						.sorted(Comparator.comparing(UserTeamRankVO::getWinNum).reversed()).collect(Collectors.toList());
				break;
			case WIN_TEAM_RATE:
				userTeamRankVOS = rankMap.values().stream()
						.filter(v -> PLACEMENT_MATCH_TEAM_LIMIT <= v.getTotalNum())
						.sorted(Comparator.comparing(UserTeamRankVO::getWinPercent).reversed()).collect(Collectors.toList());
				break;
			default:
				throw new BusinessException(SystemResultCode.BAD_REQUEST.getCode(), "错误的排行类型");
		}

		userTeamRankVOS.addAll(rankMap.values().stream().filter(v -> PLACEMENT_MATCH_TEAM_LIMIT > v.getTotalNum()).collect(Collectors.toList()));
		return userTeamRankVOS;
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


	private UserTeamRankVO fillUserTeamRankVO(String uid1, String uid2, Map<String, UserTeamRankVO> rankMap) {
		String uidL = uid1 + uid2;
		String uidR = uid2 + uid1;
		UserTeamRankVO userTeamRankVO = rankMap.get(uidL);
		if (userTeamRankVO == null) {
			userTeamRankVO = rankMap.get(uidR);
		}
		if (userTeamRankVO == null) {
			userTeamRankVO = new UserTeamRankVO();
			User user1 = userMapper.selectByUid(uid1);
			userTeamRankVO.setUser1(user1);
			User user2 = userMapper.selectByUid(uid2);
			userTeamRankVO.setUser2(user2);
		}
		rankMap.put(uidL, userTeamRankVO);
		return userTeamRankVO;
	}

}
