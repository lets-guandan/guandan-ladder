package com.guandan.ladder.service;

import com.guandan.ladder.mapper.UserGameInfoMapper;
import com.guandan.ladder.model.entity.UserGameInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hccake
 */
@Service
@RequiredArgsConstructor
public class UserGameInfoService {

	private final UserGameInfoMapper userGameInfoMapper;

	public List<UserGameInfo> listByWinNumDesc() {
		return userGameInfoMapper.listByWinNumDesc();
	}

}
