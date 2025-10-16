package com.ayhanunlu.mapper;

import org.mapstruct.Mapper;
import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.data.entity.UserEntity;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto fromUserEntityToUserDto(UserEntity userEntity);
    UserEntity fromUserDtoToUserEntity(UserDto userDto);
}
