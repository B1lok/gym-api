package com.example.gymapi.web.mapper;

import com.example.gymapi.domain.UserSubscription;
import com.example.gymapi.web.dto.userSubscription.UserSubscriptionDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSubscriptionMapper {
    UserSubscription toEntity(UserSubscriptionDto userSubscriptionDto);

    @Mapping(target = "subscriptionType", expression = "java(userSubscription.getSubscription().getSubscriptionType())")
    @Mapping(target = "subscriptionWithCoach", expression = "java(userSubscription.getSubscription().getWithCoach())")
    @Mapping(target = "coachId", source = "userSubscription",qualifiedByName = "mapCoachId")
    @Mapping(target = "coachFirstName",source = "userSubscription", qualifiedByName = "mapCoachFirstName")
    @Mapping(target = "coachLastName",source = "userSubscription",  qualifiedByName = "mapCoachLastName")
    UserSubscriptionDto toDto(UserSubscription userSubscription);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserSubscription partialUpdate(UserSubscriptionDto userSubscriptionDto, @MappingTarget UserSubscription userSubscription);

    @Named("mapCoachId")
    default Long mapCoachId(UserSubscription userSubscription) {
        return userSubscription.getSubscription().getWithCoach() ? userSubscription.getCoach().getId() : null;
    }

    @Named("mapCoachFirstName")
    default String mapCoachFirstName(UserSubscription userSubscription) {
        return userSubscription.getSubscription().getWithCoach() ? userSubscription.getCoach().getFirstName() : null;
    }
    @Named("mapCoachLastName")
    default String mapCoachLastName(UserSubscription userSubscription) {
        return userSubscription.getSubscription().getWithCoach() ? userSubscription.getCoach().getLastName() : null;
    }
}