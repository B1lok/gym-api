package com.example.gymapi.service.impl;

import com.example.gymapi.data.CoachInfoRepository;
import com.example.gymapi.data.TrainingRepository;
import com.example.gymapi.data.UserRepository;
import com.example.gymapi.data.UserSubscriptionRepository;
import com.example.gymapi.domain.CoachInfo;
import com.example.gymapi.domain.Training;
import com.example.gymapi.domain.TrainingStatus;
import com.example.gymapi.domain.User;
import com.example.gymapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final CoachInfoRepository coachInfoRepository;

    private final TrainingRepository trainingRepository;

    private final UserSubscriptionRepository userSubscriptionRepository;

    @Override
    @Transactional
    public void giveCoachRole(User user, CoachInfo coachInfo) {
        user.addCoachRole();
        userRepository.save(user);
        coachInfo.setCoach(user);
        coachInfoRepository.save(coachInfo);
    }

    @Override
    @Transactional
    public void takeCoachRole(User coach, User coachToReplace) {
        List<Training> recordsCopy = new ArrayList<>(coach.getRecords());

        recordsCopy.forEach(record -> {
            if (record.getTrainingStatus().equals(TrainingStatus.FINISHED)){
                record.setCoach(null);
                record.removeTrainingCoach(coach);
                userRepository.save(coach);
                trainingRepository.save(record);
            } else {
                record.setTrainingCoach(coachToReplace);
                userRepository.save(coachToReplace);
                trainingRepository.save(record);
            }
        });
        var coachSubscriptions = userSubscriptionRepository.findByCoachId(coach.getId());
        coachSubscriptions.forEach(subscription -> {
            if (subscription.getExpirationDate().isAfter(LocalDate.now()) && subscription.getTrainingsLeft() != 0){
                subscription.setCoach(coachToReplace);
                userSubscriptionRepository.save(subscription);
            } else {
                subscription.setCoach(null);
                userSubscriptionRepository.save(subscription);
            }
        });
        coach.removeCoachRole();
        userRepository.save(coach);
        coachInfoRepository.deleteByCoach(coach);
    }

    @Override
    @Transactional
    public void takeAdminRole(User user) {
        user.removeAdminRole();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void giveAdminRole(User user) {
        user.addAdminRole();
        userRepository.save(user);
    }
}
