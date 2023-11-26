package com.example.gymapi.data;

import com.example.gymapi.domain.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    List<UserSubscription> findByCoachId(Long coachId);
}