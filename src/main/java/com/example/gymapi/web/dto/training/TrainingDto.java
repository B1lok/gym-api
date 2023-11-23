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
public class TrainingDto implements Serializable {
    private final Long id;
    private final GymZone trainingType;
    private final LocalDate trainingDate;
    private final LocalTime trainingStart;
    private final LocalTime trainingEnd;
    private final TrainingStatus trainingStatus;
    private final Long coachId;
    private final String coachFirstName;
    private final String coachLastName;
}