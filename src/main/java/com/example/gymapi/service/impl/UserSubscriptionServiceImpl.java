package com.example.gymapi.service.impl;

import com.example.gymapi.domain.Subscription;
import com.example.gymapi.domain.User;
import com.example.gymapi.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserSubscriptionServiceImpl implements UserSubscriptionService {
    @Override
    @Transactional
    public void buySubscription(Subscription subscription, User user, Long coachId) {

    }
}
