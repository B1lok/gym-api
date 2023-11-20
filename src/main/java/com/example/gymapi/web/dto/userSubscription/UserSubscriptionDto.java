package com.example.gymapi.web.dto.userSubscription;

import com.example.gymapi.domain.GymZone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class UserSubscriptionDto implements Serializable {
    private final Long id;
    private final GymZone subscriptionType;
    private final Boolean subscriptionWithCoach;
    private final Integer trainingsLeft;
    private final Long coachId;
    private final String coachFirstName;
    private final String coachLastName;
    private final LocalDate purchaseDate;
    private final LocalDate expirationDate;
}