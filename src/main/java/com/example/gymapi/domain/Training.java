package com.example.gymapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "training_start")
    private LocalDateTime trainingStart;

    @Column(name = "training_end")
    private LocalDateTime trainingEnd;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private User coach;
}