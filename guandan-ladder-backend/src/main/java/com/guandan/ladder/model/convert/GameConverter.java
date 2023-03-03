package com.guandan.ladder.model.convert;

import com.guandan.ladder.model.dto.GameRecordDto;
import com.guandan.ladder.model.entity.GameRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author hccake
 */
@Mapper
public interface GameConverter {

	GameConverter INSTANCE = Mappers.getMapper(GameConverter.class);

	GameRecord recordDtoToEntity(GameRecordDto dto);

}
