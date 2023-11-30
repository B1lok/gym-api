package com.example.gymapi.web.mapper;

import com.example.gymapi.domain.CoachInfo;
import com.example.gymapi.web.dto.coach.CoachInfoCreationDto;
import com.example.gymapi.web.dto.coach.CoachInfoDto;
import com.example.gymapi.web.dto.coach.CoachInfoUpdateDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoachInfoMapper {
    CoachInfo toEntity(CoachInfoCreationDto coachInfoCreationDto);

    @Mapping(target = "id", expression = "java(coachInfo.getCoach().getId())")
    @Mapping(target = "coachFirstName", expression = "java(coachInfo.getCoach().getFirstName())")
    @Mapping(target = "coachLastName", expression = "java(coachInfo.getCoach().getLastName())")
    @Mapping(target = "coachEmail", expression = "java(coachInfo.getCoach().getEmail())")
    @Mapping(target = "coachPhoneNumber", expression = "java(coachInfo.getCoach().getPhoneNumber())")
    CoachInfoDto toDto(CoachInfo coachInfo);

    CoachInfoUpdateDto toDtoForCoach(CoachInfo coachInfo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CoachInfo partialUpdate(CoachInfoUpdateDto coachInfoUpdateDto, @MappingTarget CoachInfo coachInfo);
}