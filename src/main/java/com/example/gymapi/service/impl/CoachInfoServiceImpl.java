package com.example.gymapi.service.impl;

import com.example.gymapi.data.CoachInfoRepository;
import com.example.gymapi.domain.CoachInfo;
import com.example.gymapi.service.CoachInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoachInfoServiceImpl implements CoachInfoService {

    private final CoachInfoRepository coachInfoRepository;

    @Override
    public List<CoachInfo> getAll() {
        return coachInfoRepository.findAll();
    }

    @Override
    public Optional<CoachInfo> getByCoachId(Long coachId) {
        return coachInfoRepository.findByCoachId(coachId);
    }

    @Override
    @Transactional
    public CoachInfo updateCoachInfo(CoachInfo coachInfo) {
        return coachInfoRepository.save(coachInfo);
    }
}
