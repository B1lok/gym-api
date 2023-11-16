package com.example.gymapi.service.impl;

import com.example.gymapi.data.CoachInfoRepository;
import com.example.gymapi.domain.CoachInfo;
import com.example.gymapi.service.CoachInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachInfoServiceImpl implements CoachInfoService {

    private final CoachInfoRepository coachInfoRepository;

    @Override
    public List<CoachInfo> getAll() {
        return coachInfoRepository.findAll();
    }
}
