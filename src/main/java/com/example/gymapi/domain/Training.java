package com.example.gymapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_type")
    private GymZone trainingType;

    @Column(name = "training_date")
    private LocalDate trainingDate;

    @Column(name = "training_start")
    private LocalTime trainingStart;

    @Column(name = "training_end")
    private LocalTime trainingEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "training_status")
    private TrainingStatus trainingStatus;

    @ManyToOne
    @JoinColumn(name = "user_subscription_id")
    private UserSubscription userSubscription;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;

    public void setSubscription(UserSubscription userSubscription) {
        setUserSubscription(userSubscription);
        userSubscription.addTraining(this);
    }

    public void removeSubscription(UserSubscription userSubscription) {
        userSubscription.removeTraining(this);
    }


    public void setTrainingUser(User user) {
        setUser(user);
        user.addTraining(this);
    }

    public void removeTrainingUser(User user) {
        user.removeTraining(this);
    }

    public void setTrainingCoach(User coach) {
        setCoach(coach);
        coach.addRecord(this);
    }

    public void removeTrainingCoach(User coach) {
        coach.removeRecord(this);
    }
}