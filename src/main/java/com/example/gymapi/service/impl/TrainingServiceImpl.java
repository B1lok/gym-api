package com.example.gymapi.service.impl;

import com.example.gymapi.data.TrainingRepository;
import com.example.gymapi.data.UserRepository;
import com.example.gymapi.data.UserSubscriptionRepository;
import com.example.gymapi.domain.Training;
import com.example.gymapi.domain.TrainingStatus;
import com.example.gymapi.domain.User;
import com.example.gymapi.domain.UserSubscription;
import com.example.gymapi.exception.InvalidDateTimeException;
import com.example.gymapi.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TrainingServiceImpl implements TrainingService {

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final TrainingRepository trainingRepository;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void cancelTraining(Training training, UserSubscription userSubscription) {
        userSubscription.incrementTrainingsLeft();
        training.removeSubscription(training.getUserSubscription());
        training.removeTrainingUser(training.getUser());
        training.removeTrainingCoach(training.getCoach());
        userRepository.save(training.getCoach());
        userRepository.save(training.getUser());
        userSubscriptionRepository.save(userSubscription);
        trainingRepository.delete(training);
    }

    @Override
    public Optional<Training> findById(Long id) {
        return trainingRepository.findById(id);
    }

    @Override
    @Transactional
    public void createTraining(User user, Training training, UserSubscription userSubscription, User coach) {
        if (!isTrainingDateTimeCorrect(training, userSubscription)) {
            throw new InvalidDateTimeException("Invalid date/time");
        }
        training.setTrainingType(userSubscription.getSubscriptionType());
        training.setTrainingStatus(TrainingStatus.ACTIVE);
        training.setTrainingUser(user);
        training.setTrainingCoach(coach);
        training.setSubscription(userSubscription);
        userSubscription.decrementTrainingsLeft();
        userSubscriptionRepository.save(userSubscription);
        trainingRepository.save(training);
    }

    public boolean isTrainingDateTimeCorrect(Training training, UserSubscription userSubscription) {
        LocalDate trainingDate = training.getTrainingDate();
        if (userSubscription.getPurchaseDate().isAfter(trainingDate) || userSubscription.getExpirationDate().isBefore(trainingDate)) {
            return false;
        }
        return !training.getTrainingStart().isAfter(training.getTrainingEnd());
    }
}