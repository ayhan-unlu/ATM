package com.ayhanunlu.mapper;

import com.ayhanunlu.data.dto.LogDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LogMapper {
    LogMapper Instance = Mappers.getMapper(LogMapper.class);

}
