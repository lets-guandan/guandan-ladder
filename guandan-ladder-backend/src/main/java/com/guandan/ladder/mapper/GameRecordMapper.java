package com.guandan.ladder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guandan.ladder.model.entity.GameRecord;
import org.apache.ibatis.annotations.Param;

/**
 * @author hccake
 */
public interface GameRecordMapper extends BaseMapper<GameRecord> {

	/**
	 * 按位运算更新确认人
	 */
	int confirmRecord(@Param("uid") String uid, @Param("id") String id);

}
