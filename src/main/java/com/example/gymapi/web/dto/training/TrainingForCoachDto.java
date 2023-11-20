package com.example.gymapi.web.dto.training;

import com.example.gymapi.domain.GymZone;
import com.example.gymapi.domain.TrainingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link com.example.gymapi.domain.Training}
 */
@AllArgsConstructor
@Getter
public class TrainingForCoachDto implements Serializable {
    private final Long id;
    private final Long userId;
    private final String userFirstName;
    private final String userLastName;
    private final String userEmail;
    private final GymZone trainingType;
    private final LocalDate trainingDate;
    private final LocalTime trainingStart;
    private final LocalTime trainingEnd;
    private final TrainingStatus trainingStatus;
}