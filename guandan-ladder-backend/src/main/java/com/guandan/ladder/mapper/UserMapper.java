package com.guandan.ladder.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guandan.ladder.model.entity.User;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

	@Select("select * from user where username = #{userName} limit 1")
	User selectByName(@Param("userName") String userName);

	default User selectByUid(String uid) {
		LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class).eq(User::getUid, uid);
		return this.selectOne(wrapper);
	}

	@Update("update user set password = #{password}, updated_time= NOW()  where uid = #{uid}")
	void updatePassword(@Param("uid") String uid, @Param("password") String password);

}
