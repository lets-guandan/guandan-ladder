package com.guandan.ladder.service;

import cn.hutool.core.collection.CollUtil;
import com.guandan.ladder.mapper.UserMapper;
import com.guandan.ladder.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hccake
 */
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;

	public Map<String, User> listUserMapByUids(Collection<String> uids) {
		List<User> users = userMapper.selectListByUids(uids);
		if (CollUtil.isEmpty(users)) {
			return new HashMap<>(0);
		}
		return users.stream().collect(Collectors.toMap(User::getUid, Function.identity()));
	}

	public List<User> listAll() {
		return userMapper.selectList(null);
	}

	public User queryByName(String userName) {
		return userMapper.selectByName(userName);
	}

	public User queryByUid(String userId) {
		return userMapper.selectByUid(userId);
	}

	/**
	 * 修改密码
	 */
	public void updatePassword(String uid, String password) {
		userMapper.updatePassword(uid, password);
	}

}
