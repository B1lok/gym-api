package com.example.gymapi.web.dto.coach;

import com.example.gymapi.domain.GymZone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class CoachInfoDto implements Serializable {
    private final Long id;
    private final String coachFirstName;
    private final String coachLastName;
    private final String coachEmail;
    private final String coachPhoneNumber;
    private final String education;
    private final String experience;
    private final GymZone specialization;
}