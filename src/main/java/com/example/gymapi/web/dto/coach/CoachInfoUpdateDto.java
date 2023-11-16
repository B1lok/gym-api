package com.example.gymapi.web.dto.coach;

import com.example.gymapi.domain.GymZone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class CoachInfoUpdateDto implements Serializable {
    private final String education;
    private final String experience;
    private final GymZone specialization;
}