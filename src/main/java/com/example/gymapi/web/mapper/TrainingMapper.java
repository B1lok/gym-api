package com.example.gymapi.web.mapper;

import com.example.gymapi.domain.Training;
import com.example.gymapi.domain.UserSubscription;
import com.example.gymapi.web.dto.training.TrainingCreationDto;
import com.example.gymapi.web.dto.training.TrainingDto;
import com.example.gymapi.web.dto.training.TrainingForCoachDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TrainingMapper {
    Training toEntity(TrainingCreationDto trainingCreationDto);

    @Mapping(target = "coachId", source = "training",qualifiedByName = "mapCoachId")
    @Mapping(target = "coachFirstName",source = "training", qualifiedByName = "mapCoachFirstName")
    @Mapping(target = "coachLastName", source = "training",qualifiedByName = "mapCoachLastName")
    TrainingDto toDto(Training training);

    @Mapping(target = "userId", expression = "java(training.getUser().getId())")
    @Mapping(target = "userFirstName", expression = "java(training.getUser().getFirstName())")
    @Mapping(target = "userLastName", expression = "java(training.getUser().getLastName())")
    @Mapping(target = "userEmail", expression = "java(training.getUser().getEmail())")
    TrainingForCoachDto toTrainingForCoachDto(Training training);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Training partialUpdate(TrainingDto trainingDto, @MappingTarget Training training);

    @Named("mapCoachId")
    default Long mapCoachId(Training training) {
        return training.getCoach() == null ? null : training.getCoach().getId();
    }

    @Named("mapCoachFirstName")
    default String mapCoachFirstName(Training training) {
        return training.getCoach() == null ? null : training.getCoach().getFirstName();
    }
    @Named("mapCoachLastName")
    default String mapCoachLastName(Training training) {
        return training.getCoach() == null ? null : training.getCoach().getLastName();
    }
}