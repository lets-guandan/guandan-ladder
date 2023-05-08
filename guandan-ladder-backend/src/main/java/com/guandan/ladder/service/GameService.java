package com.guandan.ladder.service;

import com.guandan.ladder.constant.UnConfirmTypeEnum;
import com.guandan.ladder.mapper.GameRecordMapper;
import com.guandan.ladder.mapper.UserGameInfoMapper;
import com.guandan.ladder.model.convert.GameConverter;
import com.guandan.ladder.model.dto.ConfirmRecordDto;
import com.guandan.ladder.model.dto.GameRecordDto;
import com.guandan.ladder.model.dto.GameRecordOutDto;
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
	public List<GameRecord> gameList(String uid) {
		List<GameRecord> list = gameRecordMapper.selectValidRecords(uid);
		return list == null ? new ArrayList<>() : list;
	}

	/**
	 * 待确认战绩列表
	 * @param unConfirmTypeEnum 待确认查询范围枚举
	 */
	public List<GameRecord> unconfirmedRecordList(UnConfirmTypeEnum unConfirmTypeEnum) {
		String uid = SecurityContext.getUserId();
		// 查询与当前用户有关的 且 自己或者其他人还没确认的 战绩
		List<GameRecord> list;
		if (UnConfirmTypeEnum.MY.equals(unConfirmTypeEnum)) {
			list = gameRecordMapper.selectUnconfirmedList(uid);
		}
		else {
			// 空或者2查所有人的
			list = gameRecordMapper.selectInValidRecords(uid);
		}
		return list == null ? new ArrayList<>() : list;
	}

	/**
	 * 确认战绩
	 * @param confirmRecordDto 入参 用户id和对局id
	 */
	@Transactional(rollbackFor = Exception.class)
	public void confirmRecord(ConfirmRecordDto confirmRecordDto) {
		String userId = SecurityContext.getUserId();
		int i = gameRecordMapper.confirmRecord(userId, confirmRecordDto.getRecordId());
		if (i > 0) {
			GameRecord gameRecord = gameRecordMapper.selectById(confirmRecordDto.getRecordId());
			// 如果都确认了 则记录到历史战绩
			if (gameRecord != null && 15 == gameRecord.getUserConfirmFlagBits()) {
				// 对战48小时内需确认 另要置为无效状态 不能查出
				// boolean before =
				// gameRecord.getGameTime().plusHours(48).isBefore(LocalDateTime.now());
				// Assert.isTrue(before, "战绩确认时效是48小时，此局对战时间-{}",
				// gameRecord.getGameTime());
				userGameInfoMapper.incrWinNumAndTotalNum(gameRecord.getWinUid1(), gameRecord.getWinUid2());
				userGameInfoMapper.incrTotalNum(gameRecord.getLoseUid1(), gameRecord.getLoseUid2());
			}
		}
	}

}
