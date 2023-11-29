package com.example.gymapi.service;

import com.example.gymapi.domain.Training;
import com.example.gymapi.domain.User;
import com.example.gymapi.domain.UserSubscription;

import java.util.List;
import java.util.Optional;

public interface TrainingService {
    void createTraining(User user, Training training, UserSubscription userSubscription, User coach);

    void cancelTraining(Training training, UserSubscription userSubscription);

    Optional<Training> findById(Long id);

    List<Training> getTrainingsByUserSubscriptionId(Long userSubscriptionId);
}
