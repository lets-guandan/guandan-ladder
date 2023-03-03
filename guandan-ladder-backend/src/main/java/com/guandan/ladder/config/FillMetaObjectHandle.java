package com.guandan.ladder.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hccake.ballcat.common.core.constant.GlobalConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Hccake
 * @version 1.0
 */
@Slf4j
@Component
public class FillMetaObjectHandle implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		// 逻辑删除标识
		this.strictInsertFill(metaObject, "deleted", Long.class, GlobalConstants.NOT_DELETED_FLAG);
		// 创建时间
		this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		// 修改时间
		this.strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
	}

}
