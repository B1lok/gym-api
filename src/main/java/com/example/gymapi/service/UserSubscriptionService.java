package com.example.gymapi.service;

import com.example.gymapi.domain.Subscription;
import com.example.gymapi.domain.User;

public interface UserSubscriptionService {

    void buySubscription(Subscription subscription, User user, Long coachId);
}
