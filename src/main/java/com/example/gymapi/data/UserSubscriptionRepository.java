package com.example.gymapi.data;

import com.example.gymapi.domain.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    List<UserSubscription> findByCoachId(Long coachId);

    @Query("SELECT us FROM UserSubscription us WHERE MONTH(us.purchaseDate) = :month AND YEAR(us.purchaseDate) = :year")
    List<UserSubscription> findSubscriptionsSoldInMonth(@Param("month") int month, @Param("year") int year);
}