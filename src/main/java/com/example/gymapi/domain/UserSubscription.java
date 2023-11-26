package com.example.gymapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_subscription")
public class UserSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(name = "trainings_left")
    private Integer trainingsLeft;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_type")
    private GymZone subscriptionType;

    @Column(name = "with_coach")
    private Boolean subscriptionWithCoach;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @OneToMany(mappedBy = "userSubscription", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();

    public void addUser(User user) {
        this.setUser(user);
        user.addSubscription(this);
    }

    public void addSubscription(Subscription subscription) {
        this.setSubscription(subscription);
        subscription.addUserSubscription(this);
    }

    public void removeSubscription(Subscription subscription){
        this.setSubscription(null);
        subscription.removeUserSubscription(this);
    }

    public void addTraining(Training training) {
        trainings.add(training);
    }

    public void removeTraining(Training training) {
        trainings.remove(training);
    }

    public void incrementTrainingsLeft() {
        this.setTrainingsLeft(trainingsLeft + 1);
    }

    public void decrementTrainingsLeft() {
        this.setTrainingsLeft(trainingsLeft - 1);
    }
}