package com.example.gymapi.web.dto.training;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TrainingCreationDto implements Serializable {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    @NotNull
    private final LocalDate trainingDate;

    @DateTimeFormat(pattern = "HH:mm:ss")
    @NotNull
    private final LocalTime trainingStart;

    @DateTimeFormat(pattern = "HH:mm:ss")
    @NotNull
    private final LocalTime trainingEnd;
}