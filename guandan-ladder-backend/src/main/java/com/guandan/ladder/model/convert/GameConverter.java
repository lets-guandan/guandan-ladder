package com.guandan.ladder.model.convert;

import com.guandan.ladder.model.dto.GameRecordDto;
import com.guandan.ladder.model.dto.GameRecordUnConfirmOutDto;
import com.guandan.ladder.model.entity.GameRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author hccake
 */
@Mapper
public interface GameConverter {

	GameConverter INSTANCE = Mappers.getMapper(GameConverter.class);

	GameRecord recordDtoToEntity(GameRecordDto dto);

	@Mappings({
			@Mapping(target = "userConfirmFlag", source = "userConfirmFlagBits",
					qualifiedByName = "confirmFlagCalculator")
	})
	GameRecordUnConfirmOutDto recordEntityToOutDto(GameRecord dto);

	/**
	 * 将十进制数字转成二进制字符串显示，可以判断谁确认了
	 *  * 0001=1 败者2
	 *  * 0010=2 败者1
	 *  * 0011=3
	 *  * 0100=4 胜者2
	 *  * 0101=5
	 *  * 0110=6
	 *  * 0111=7
	 *  * 1000=8 胜者1
	 *  * 1001=9
	 *  * 1010=10
	 *  * 1011=11
	 *  * 1100=12
	 *  * 1101=13
	 *  * 1110=14
	 *  * 1111=15
	 */
	@Named("confirmFlagCalculator")
	default String confirmFlagCalculator(Integer userConfirmFlagBits) {
		return Integer.toBinaryString(userConfirmFlagBits);
	}


}
