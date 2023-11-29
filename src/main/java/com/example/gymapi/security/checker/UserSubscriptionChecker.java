package com.example.gymapi.security.checker;

import com.example.gymapi.data.UserRepository;
import com.example.gymapi.data.UserSubscriptionRepository;
import com.example.gymapi.exception.SubscriptionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSubscriptionChecker {

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final UserRepository userRepository;

    public boolean checkForGettingTrainings(Long userSubscriptionId, String userEmail){
        var user = userRepository.findByEmail(userEmail).get();

        var userSubscription = userSubscriptionRepository.findById(userSubscriptionId).orElseThrow(
                () -> new SubscriptionNotFoundException("Subscription wasn't found")
        );

        return userSubscription.getUser().getId().equals(user.getId());

    }

}
