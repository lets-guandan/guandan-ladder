package com.guandan.ladder.service;

import cn.hutool.core.collection.CollUtil;
import com.guandan.ladder.mapper.UserGameInfoMapper;
import com.guandan.ladder.model.convert.UserConverter;
import com.guandan.ladder.model.dto.RankDTO;
import com.guandan.ladder.model.entity.User;
import com.guandan.ladder.model.entity.UserGameInfo;
import com.guandan.ladder.model.vo.UserRankVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author hccake
 */
@Service
@RequiredArgsConstructor
public class UserGameInfoService {

	private final UserService userService;

	private final UserGameInfoMapper userGameInfoMapper;

	public List<UserRankVO> rank(@RequestBody RankDTO rankDTO) {
		List<UserGameInfo> userGameInfoList;
		//胜场 or 胜率
		if("winNum".equals(rankDTO.getTag())){
			userGameInfoList = userGameInfoMapper.listByWinNumDesc();
		}else {
			userGameInfoList = userGameInfoMapper.listByWinPercentDesc();
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
			BigDecimal divide = new BigDecimal(userGameInfo.getWinNum()).divide(
					new BigDecimal(userGameInfo.getTotalNum()), 2, RoundingMode.HALF_UP);
			userRankVO.setWinPercent(divide);
			UserConverter.INSTANCE.fillUserRankVo(userRankVO, userMap.get(userGameInfo.getUid()));
			list.add(userRankVO);
		}

		return list;
	}

}
