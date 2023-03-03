package com.guandan.ladder.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guandan.ladder.model.entity.User;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;

import java.util.Collection;
import java.util.List;

/**
 * @author hccake
 */
public interface UserMapper extends ExtendMapper<User> {

	default List<User> selectListByUids(Collection<String> uids) {
		LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).in(User::getUid, uids);
		return this.selectList(wrapper);
	}

}
