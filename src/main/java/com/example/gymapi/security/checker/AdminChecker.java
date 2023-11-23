package com.example.gymapi.security.checker;

import com.example.gymapi.data.UserRepository;
import com.example.gymapi.domain.Role;
import com.example.gymapi.domain.TrainingStatus;
import com.example.gymapi.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminChecker {

    public final UserRepository userRepository;

    public boolean checkIsUserCustomer(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with this id doesn't exist")
        );

        return user.getRoles().size() == 1 && user.getRoles().contains(Role.USER);
    }

    public boolean checkForGivingAdminRole(Long userId){
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with this id doesn't exist")
        );
        if (user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.COACH)) return false;

        return true;
    }

    public boolean checkForTakingAdminRole(Long userId, String adminEmail){
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with this id doesn't exist")
        );

        var admin = userRepository.findByEmail(adminEmail).get();

        if (user.getRoles().contains(Role.COACH) || !user.getRoles().contains(Role.ADMIN)) return false;

        return !admin.getId().equals(user.getId());
    }

    public boolean checkForGivingCoachRole(Long userId){
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with this id doesn't exist")
        );

        if (user.getRoles().contains(Role.ADMIN) || user.getRoles().contains(Role.COACH)) return false;

        return user.getTrainings().stream()
                .noneMatch(training -> training.getTrainingStatus().equals(TrainingStatus.ACTIVE));
    }

    public boolean checkForTakingCoachRole(Long userId){
        var user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with this id doesn't exist")
        );

        return user.getRoles().contains(Role.COACH);
    }
}
