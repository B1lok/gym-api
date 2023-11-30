package com.example.gymapi.service.impl;

import com.example.gymapi.data.UserSubscriptionRepository;
import com.example.gymapi.domain.*;
import com.example.gymapi.exception.CoachNotFoundException;
import com.example.gymapi.exception.SubscriptionNotAllowedException;
import com.example.gymapi.service.CoachInfoService;
import com.example.gymapi.service.UserService;
import com.example.gymapi.service.UserSubscriptionService;
import com.example.gymapi.web.dto.userSubscription.UserSubscriptionCreationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final CoachInfoService coachInfoService;

    private final UserService userService;


    @Override
    public Optional<UserSubscription> findById(Long id) {
        return userSubscriptionRepository.findById(id);
    }


    @Override
    @Transactional
    public void buySubscription(Subscription subscription, String email, UserSubscriptionCreationDto dto) {
        User user = userService.findByEmail(email).get();
        if (!isUserAllowedToBuySubscription(user.getUserSubscriptions(), subscription.getSubscriptionType(), dto.getPurchaseDate())) {
            throw new SubscriptionNotAllowedException("You already have this type of subscription");
        }
        var userSubscription = new UserSubscription();
        if (subscription.getWithCoach()) {
            if (dto.getCoachId() == null) throw new CoachNotFoundException("Choose coach");
            CoachInfo coachInfo = coachInfoService.getByCoachId(dto.getCoachId()).orElseThrow(
                    () -> new CoachNotFoundException("Coach not found")
            );
            if (!coachInfo.getSpecialization().equals(subscription.getSubscriptionType())) {
                throw new CoachNotFoundException("Select coach with appropriate specialization");
            }
            userSubscription.setCoach(coachInfo.getCoach());
        }
        userSubscription.setSubscriptionType(subscription.getSubscriptionType());
        userSubscription.setSubscriptionWithCoach(subscription.getWithCoach());
        userSubscription.setPurchaseDate(dto.getPurchaseDate());
        userSubscription.setExpirationDate(dto.getPurchaseDate().plusDays(subscription.getDurationInDays()));
        userSubscription.addSubscription(subscription);
        userSubscription.setTrainingsLeft(subscription.getVisitsAmount());
        userSubscription.addUser(user);
        userSubscriptionRepository.save(userSubscription);
    }


    boolean isUserAllowedToBuySubscription(Set<UserSubscription> subscriptions, GymZone subscriptionType, LocalDate purchaseDate) {
        return subscriptions.stream()
                .filter(subscription -> subscription.getSubscriptionType().equals(subscriptionType))
                .noneMatch(subscription -> purchaseDate.isBefore(subscription.getExpirationDate()) && subscription.getTrainingsLeft() != 0);
    }
}