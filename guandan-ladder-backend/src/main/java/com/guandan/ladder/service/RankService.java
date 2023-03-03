package com.guandan.ladder.service;

import com.guandan.ladder.mapper.UserMapper;
import com.guandan.ladder.mapper.UserGameInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 天梯 Service
 *
 * @author hccake
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RankService {

	private UserMapper userMapper;

	private UserGameInfoMapper userGameInfoMapper;

}
