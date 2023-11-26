package com.example.gymapi.security.checker;

import com.example.gymapi.data.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SubscriptionChecker {

    private final SubscriptionRepository subscriptionRepository;

    public boolean check(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId).isPresent();
    }

}
