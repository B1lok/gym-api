package com.example.gymapi.web.mapper;

import com.example.gymapi.domain.Training;
import com.example.gymapi.web.dto.training.TrainingCreationDto;
import com.example.gymapi.web.dto.training.TrainingDto;
import com.example.gymapi.web.dto.training.TrainingForCoachDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TrainingMapper {
    Training toEntity(TrainingCreationDto trainingCreationDto);

    @Mapping(target = "coachId", expression = "java(training.getCoach().getId())")
    @Mapping(target = "coachFirstName", expression = "java(training.getCoach().getFirstName())")
    @Mapping(target = "coachLastName", expression = "java(training.getCoach().getLastName())")
    TrainingDto toDto(Training training);

    @Mapping(target = "userId", expression = "java(training.getUser().getId())")
    @Mapping(target = "userFirstName", expression = "java(training.getUser().getFirstName())")
    @Mapping(target = "userLastName", expression = "java(training.getUser().getLastName())")
    @Mapping(target = "userEmail", expression = "java(training.getUser().getEmail())")
    TrainingForCoachDto toTrainingForCoachDto(Training training);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Training partialUpdate(TrainingDto trainingDto, @MappingTarget Training training);
}