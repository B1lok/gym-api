package com.example.gymapi.service.impl;

import com.example.gymapi.data.CoachInfoRepository;
import com.example.gymapi.data.UserRepository;
import com.example.gymapi.domain.CoachInfo;
import com.example.gymapi.domain.User;
import com.example.gymapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    private final CoachInfoRepository coachInfoRepository;

    @Override
    @Transactional
    public void giveCoachRole(User user, CoachInfo coachInfo) {
        user.addCoachRole();
        userRepository.save(user);
        coachInfo.setCoach(user);
        coachInfoRepository.save(coachInfo);
    }

    @Override
    @Transactional
    public void takeCoachRole(User user) {
        user.removeCoachRole();
        userRepository.save(user);
        coachInfoRepository.deleteByCoach(user);
    }

    @Override
    @Transactional
    public void takeAdminRole(User user) {
        user.removeAdminRole();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void giveAdminRole(User user) {
        user.addAdminRole();
        userRepository.save(user);
    }
}
