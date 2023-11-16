package com.example.gymapi.web.dto.subscription;

import com.example.gymapi.domain.GymZone;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubscriptionCreationDto {
    private final GymZone subscriptionType;
    private final BigDecimal price;
    private final Integer visitsAmount;
    private final Integer durationInDays;
    private final Boolean withCoach;
}
