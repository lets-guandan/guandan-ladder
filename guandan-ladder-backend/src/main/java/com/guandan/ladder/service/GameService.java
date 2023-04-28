package com.guandan.ladder.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guandan.ladder.mapper.GameRecordMapper;
import com.guandan.ladder.mapper.UserGameInfoMapper;
import com.guandan.ladder.model.convert.GameConverter;
import com.guandan.ladder.model.dto.ConfirmRecordDto;
import com.guandan.ladder.model.dto.GameRecordDto;
import com.guandan.ladder.model.dto.GameRecordOutDto;
import com.guandan.ladder.model.dto.GameRecordUnConfirmOutDto;
import com.guandan.ladder.model.entity.GameRecord;
import com.guandan.ladder.security.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hccake
 */
@Service
@RequiredArgsConstructor
public class GameService {

	private final GameRecordMapper gameRecordMapper;

	private final UserGameInfoMapper userGameInfoMapper;

	@Transactional(rollbackFor = Exception.class)
	public void saveRecord(GameRecordDto gameRecordDto) {
		String userId = SecurityContext.getUserId();
		GameRecord gameRecord = GameConverter.INSTANCE.recordDtoToEntity(gameRecordDto);
		if (userId.equals(gameRecordDto.getWinUid1())) {
			gameRecord.setUserConfirmFlagBits(8);
		}
		else if (userId.equals(gameRecordDto.getWinUid2())) {
			gameRecord.setUserConfirmFlagBits(4);
		}
		else if (userId.equals(gameRecordDto.getLoseUid1())) {
			gameRecord.setUserConfirmFlagBits(2);
		}
		else if (userId.equals(gameRecordDto.getLoseUid2())) {
			gameRecord.setUserConfirmFlagBits(1);
		}
		gameRecordMapper.insert(gameRecord);
	}

	/**
	 * 历史战绩列表
	 */
	public List<GameRecordOutDto> gameList() {
		String uid = SecurityContext.getUserId();
		// 查询参与对局 且 等于15的表示 确认完成的
		LambdaQueryWrapper<GameRecord> wrapper = Wrappers.lambdaQuery(GameRecord.class)
			.eq(GameRecord::getUserConfirmFlagBits, 15)
			.and(w -> w.eq(GameRecord::getWinUid1, uid)
				.or()
				.eq(GameRecord::getWinUid2, uid)
				.or()
				.eq(GameRecord::getLoseUid1, uid)
				.or()
				.eq(GameRecord::getLoseUid2, uid))
			.orderByDesc(GameRecord::getGameTime);
		List<GameRecord> list = gameRecordMapper.selectList(wrapper);
		if (list == null) {
			return new ArrayList<>();
		}
		return list.stream().map(GameConverter.INSTANCE::recordOutDtoToEntity).collect(Collectors.toList());
	}

	/**
	 * 待确认战绩列表
	 */
	public List<GameRecordUnConfirmOutDto> umConfirmList() {
		String uid = SecurityContext.getUserId();
		// 查询参与对局 且 不等于15的表示未确认完成的
		LambdaQueryWrapper<GameRecord> wrapper = Wrappers.lambdaQuery(GameRecord.class)
			.ne(GameRecord::getUserConfirmFlagBits, 15)
			.and(w -> w.eq(GameRecord::getWinUid1, uid)
				.or()
				.eq(GameRecord::getWinUid2, uid)
				.or()
				.eq(GameRecord::getLoseUid1, uid)
				.or()
				.eq(GameRecord::getLoseUid2, uid))
			.orderByDesc(GameRecord::getGameTime);
		List<GameRecord> list = gameRecordMapper.selectList(wrapper);
		if (list == null) {
			return new ArrayList<>();
		}
		return list.stream().map(GameConverter.INSTANCE::recordEntityToOutDto).collect(Collectors.toList());
	}

	/**
	 * 确认战绩
	 * @param confirmRecordDto 入参 用户id和对局id
	 */
	@Transactional(rollbackFor = Exception.class)
	public void confirmRecord(ConfirmRecordDto confirmRecordDto) {
		String userId = SecurityContext.getUserId();
		gameRecordMapper.confirmRecord(userId, confirmRecordDto.getRecordId());
		GameRecord gameRecord = gameRecordMapper.selectById(confirmRecordDto.getRecordId());
		// 如果都确认了 则记录到历史战绩
		if (gameRecord != null && 15 == gameRecord.getUserConfirmFlagBits()) {
			userGameInfoMapper.incrWinNumAndTotalNum(gameRecord.getWinUid1(), gameRecord.getWinUid2());
			userGameInfoMapper.incrTotalNum(gameRecord.getLoseUid1(), gameRecord.getLoseUid2());
		}
	}

}
