package com.example.gymapi.security.checker;


import com.example.gymapi.data.UserRepository;
import com.example.gymapi.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleChecker {

    private final UserRepository userRepository;

    public boolean checkForAllowedRoles(String userEmail){
        var user = userRepository.findByEmail(userEmail).get();

        return !user.getRoles().contains(Role.COACH) && !user.getRoles().contains(Role.ADMIN);
    }
}
