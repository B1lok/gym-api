package com.example.gymapi.service;

import com.example.gymapi.domain.CoachInfo;

import java.util.List;
import java.util.Optional;

public interface CoachInfoService {

    List<CoachInfo> getAll();

    Optional<CoachInfo> getByCoachId(Long coachId);
}
