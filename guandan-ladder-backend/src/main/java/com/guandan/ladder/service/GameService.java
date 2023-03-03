package com.guandan.ladder.service;

import com.guandan.ladder.mapper.GameRecordMapper;
import com.guandan.ladder.mapper.UserGameInfoMapper;
import com.guandan.ladder.model.convert.GameConverter;
import com.guandan.ladder.model.dto.GameRecordDto;
import com.guandan.ladder.model.entity.GameRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		GameRecord gameRecord = GameConverter.INSTANCE.recordDtoToEntity(gameRecordDto);
		gameRecordMapper.insert(gameRecord);

		// TODO 需要用户确认后再进行用户比赛信息的更新
		userGameInfoMapper.incrWinNumAndTotalNum(gameRecord.getWinUid1(), gameRecord.getWinUid2());
		userGameInfoMapper.incrTotalNum(gameRecord.getLoseUid1(), gameRecord.getLoseUid2());
	}

}
