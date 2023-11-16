package com.example.gymapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "coach_info")
public class CoachInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "coach_id")
    private User coach;

    @Column(name = "education")
    private String education;

    @Column(name = "experience")
    private String experience;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization")
    private GymZone specialization;

}