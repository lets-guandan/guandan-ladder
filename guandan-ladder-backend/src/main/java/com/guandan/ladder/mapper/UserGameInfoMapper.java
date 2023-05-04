package com.guandan.ladder.mapper;

import com.guandan.ladder.model.entity.UserGameInfo;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author hccake
 */
public interface UserGameInfoMapper extends ExtendMapper<UserGameInfo> {

	/**
	 * 根据胜场倒叙
	 */
	@Select("select uid, win_num, total_num from user_game_info where total_num !=0 "
			+ "order by win_num, win_num/user_game_info.total_num desc")
	List<UserGameInfo> listByWinNumDesc();

	/**
	 * 根据胜率倒叙
	 */
	@Select("select uid, win_num, total_num from user_game_info where total_num !=0 "
			+ "order by win_num/user_game_info.total_num, win_num desc")
	List<UserGameInfo> listByWinPercentDesc();

	@Update("update user_game_info set win_num = win_num + 1, total_num = total_num + 1  where uid = #{uid1} or uid = #{uid2}")
	void incrWinNumAndTotalNum(@Param("uid1") String uid1, @Param("uid2") String uid2);

	@Update("update user_game_info set total_num = total_num + 1  where uid = #{uid1} or uid = #{uid2}")
	void incrTotalNum(@Param("uid1") String uid1, @Param("uid2") String uid2);

}
