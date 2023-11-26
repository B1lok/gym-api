package com.example.gymapi.service.impl;

import com.example.gymapi.data.SubscriptionRepository;
import com.example.gymapi.data.UserSubscriptionRepository;
import com.example.gymapi.domain.Subscription;
import com.example.gymapi.exception.SubscriptionNotFoundException;
import com.example.gymapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOne(Subscription subscription) {
        subscription.getUserSubscriptions().forEach(userSubscription -> {
            userSubscription.removeSubscription(subscription);
            userSubscriptionRepository.save(userSubscription);
        });
        subscriptionRepository.delete(subscription);
    }

    @Override
    @Transactional
    public Subscription updateOne(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public Subscription createOne(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new SubscriptionNotFoundException("Such subscription doesn't exist"));
    }
}
