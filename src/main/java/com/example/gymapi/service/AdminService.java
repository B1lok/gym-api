package com.example.gymapi.service;

import com.example.gymapi.domain.CoachInfo;
import com.example.gymapi.domain.User;
import com.example.gymapi.web.dto.statistic.StatisticDto;

public interface AdminService {

    void giveAdminRole(User user);

    void takeAdminRole(User user);

    void giveCoachRole(User user, CoachInfo coachInfo);

    void takeCoachRole(User coach, User coachToReplace);

    StatisticDto getStatistic();

}
