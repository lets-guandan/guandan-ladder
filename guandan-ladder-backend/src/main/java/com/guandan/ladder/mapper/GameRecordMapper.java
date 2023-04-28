package com.guandan.ladder.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.guandan.ladder.model.entity.GameRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hccake
 */
public interface GameRecordMapper extends BaseMapper<GameRecord> {

	/**
	 * 按位运算更新确认人
	 */
	int confirmRecord(@Param("uid") String uid, @Param("id") String id);

	/**
	 * 查询前用户的有效历史战绩
	 * @param uid 当前用户id
	 * @return List<GameRecord>
	 */
	default List<GameRecord> selectValidRecords(String uid) {
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
		return this.selectList(wrapper);
	}

	/**
	 * 查询前用户的未确认列表
	 * @param uid 当前用户id
	 * @return List<GameRecord>
	 */
	List<GameRecord> selectUnconfirmedList(@Param("uid") String uid);


}
