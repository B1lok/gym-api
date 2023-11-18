package com.example.gymapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type")
    private GymZone subscriptionType;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "visits_amount")
    private Integer visitsAmount;

    @Column(name = "duration_in_days")
    private Integer durationInDays;

    @Column(name = "with_coach")
    private Boolean withCoach;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    private Set<UserSubscription> userSubscriptions = new LinkedHashSet<>();

    public void addUserSubscription(UserSubscription userSubscription){
        userSubscriptions.add(userSubscription);
    }
}