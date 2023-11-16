package com.example.gymapi.service.impl;

import com.example.gymapi.data.SubscriptionRepository;
import com.example.gymapi.domain.Subscription;
import com.example.gymapi.exception.SubscriptionNotFoundException;
import com.example.gymapi.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteOne(Long id) {
        subscriptionRepository.deleteById(id);
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
