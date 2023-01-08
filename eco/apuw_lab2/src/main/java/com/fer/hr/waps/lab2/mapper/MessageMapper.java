package com.fer.hr.waps.lab2.mapper;


import com.fer.hr.waps.lab2.domain.entity.MessageEntity;
import com.fer.hr.waps.lab2.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    Message fromEntity(MessageEntity entity);

    MessageEntity toEntity(Message model);
}