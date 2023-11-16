package com.example.gymapi.web.mapper;

import com.example.gymapi.domain.Subscription;
import com.example.gymapi.web.dto.subscription.SubscriptionCreationDto;
import com.example.gymapi.web.dto.subscription.SubscriptionDto;
import com.example.gymapi.web.dto.subscription.SubscriptionUpdateDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {
    Subscription toEntity(SubscriptionCreationDto subscriptionCreationDto);

    SubscriptionDto toDto(Subscription subscription);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Subscription partialUpdate(SubscriptionUpdateDto subscriptionUpdateDto, @MappingTarget Subscription subscription);
}