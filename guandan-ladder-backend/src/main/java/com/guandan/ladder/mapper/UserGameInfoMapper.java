package com.guandan.ladder.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guandan.ladder.model.entity.UserGameInfo;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author hccake
 */
public interface UserGameInfoMapper extends ExtendMapper<UserGameInfo> {

	default List<UserGameInfo> listByWinNumDesc() {
		LambdaQueryWrapper<UserGameInfo> wrapper = Wrappers.lambdaQuery(UserGameInfo.class)
				.orderByDesc(UserGameInfo::getWinNum);
		return this.selectList(wrapper);
	}

	@Update("update user_game_info set win_num = win_num + 1, total_num = total_num + 1  where uid = #{uid1} or uid = #{uid2}")
	void incrWinNumAndTotalNum(@Param("uid1") String uid1, @Param("uid2") String uid2);

	@Update("update user_game_info set total_num = total_num + 1  where uid = #{uid1} or uid = #{uid2}")
	void incrTotalNum(@Param("uid1") String uid1, @Param("uid2") String uid2);

}
