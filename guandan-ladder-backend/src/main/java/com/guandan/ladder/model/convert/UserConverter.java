package com.guandan.ladder.model.convert;

import com.guandan.ladder.model.entity.User;
import com.guandan.ladder.model.vo.UserRankVO;
import com.guandan.ladder.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author hccake
 */
@Mapper
public interface UserConverter {

	UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

	void fillUserRankVo(@MappingTarget UserRankVO userRankVO, User user);

	UserVO userToVo(User user);

}
