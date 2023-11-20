package com.example.gymapi.service;

import com.example.gymapi.domain.User;

public interface AdminService {

    void giveAdminRole(User user);

    void takeAdminRole(User user);

    void giveCoachRole(User user);

    void takeCoachRole(User user);

}
