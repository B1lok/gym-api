package com.example.gymapi.security.checker;

import com.example.gymapi.data.TrainingRepository;
import com.example.gymapi.data.UserRepository;
import com.example.gymapi.data.UserSubscriptionRepository;
import com.example.gymapi.domain.TrainingStatus;
import com.example.gymapi.exception.SubscriptionNotFoundException;
import com.example.gymapi.exception.TrainingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingChecker {

    private final UserRepository userRepository;

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final TrainingRepository trainingRepository;

    public boolean checkForEnroll(String userEmail, Long userSubscriptionId, Long coachId) {
        var user = userRepository.findByEmail(userEmail).get();

        var userSubscription = userSubscriptionRepository.findById(userSubscriptionId).orElseThrow(
                () -> new SubscriptionNotFoundException("Subscription not found")
        );
        return userSubscription.getUser().getId().equals(user.getId()) && userSubscription.getCoach().getId().equals(coachId)
                && userSubscription.getTrainingsLeft() != 0;
    }

    public boolean checkForCancel(String userEmail, Long trainingId) {
        var user = userRepository.findByEmail(userEmail).get();

        var training = trainingRepository.findById(trainingId).orElseThrow(
                () -> new TrainingNotFoundException("Training wasn't found")
        );
        return training.getUser().getId().equals(user.getId())
                && training.getTrainingStatus().equals(TrainingStatus.ACTIVE);
    }

    public boolean checkForCoachCancel(String coachEmail, Long recordId) {
        var coach = userRepository.findByEmail(coachEmail).get();

        var record = trainingRepository.findById(recordId).orElseThrow(
                () -> new TrainingNotFoundException("Record wasn't found")
        );
        return record.getCoach().getId().equals(coach.getId()) && record.getTrainingStatus().equals(TrainingStatus.ACTIVE);
    }
}
