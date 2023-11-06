package com.example.gymapi.web.mapper;

import com.example.gymapi.domain.User;
import com.example.gymapi.web.dto.user.UserCreationDto;
import com.example.gymapi.web.dto.user.UserDto;
import com.example.gymapi.web.dto.user.UserUpdateDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User toEntity(UserCreationDto userCreationDto);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserUpdateDto updateDto, @MappingTarget User user);
}