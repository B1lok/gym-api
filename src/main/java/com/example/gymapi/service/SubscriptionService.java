package com.example.gymapi.service;


import com.example.gymapi.domain.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    List<Subscription> findAll();

    Subscription getById(Long id);

    Subscription createOne(Subscription subscription);

    Subscription updateOne(Subscription subscription);

    void deleteOne(Long id);
}
