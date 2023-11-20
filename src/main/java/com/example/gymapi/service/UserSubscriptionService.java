package com.example.gymapi.service;

import com.example.gymapi.domain.Subscription;
import com.example.gymapi.domain.User;
import com.example.gymapi.domain.UserSubscription;
import com.example.gymapi.web.dto.userSubscription.UserSubscriptionCreationDto;

import java.util.Optional;

public interface UserSubscriptionService {

    void buySubscription(Subscription subscription, String email, UserSubscriptionCreationDto dto);

    Optional<UserSubscription> findById(Long id);
}
