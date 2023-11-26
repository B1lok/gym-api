package com.example.gymapi.web.controller;


import com.example.gymapi.domain.Training;
import com.example.gymapi.service.AdminService;
import com.example.gymapi.service.CoachInfoService;
import com.example.gymapi.service.UserService;
import com.example.gymapi.web.dto.coach.CoachInfoCreationDto;
import com.example.gymapi.web.dto.coach.CoachInfoDto;
import com.example.gymapi.web.dto.training.TrainingDto;
import com.example.gymapi.web.dto.training.TrainingForCoachDto;
import com.example.gymapi.web.dto.user.UserDto;
import com.example.gymapi.web.dto.userSubscription.UserSubscriptionDto;
import com.example.gymapi.web.mapper.CoachInfoMapper;
import com.example.gymapi.web.mapper.TrainingMapper;
import com.example.gymapi.web.mapper.UserMapper;
import com.example.gymapi.web.mapper.UserSubscriptionMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Admin Controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/gym/admin")
public class AdminController {

    private final UserService userService;

    private final AdminService adminService;

    private final UserMapper userMapper;

    private final UserSubscriptionMapper userSubscriptionMapper;

    private final TrainingMapper trainingMapper;

    private final CoachInfoMapper coachInfoMapper;

    private final CoachInfoService coachInfoService;

    @GetMapping("/getCustomers")
    public ResponseEntity<List<UserDto>> getAllCustomers(){
        return ResponseEntity.ok(userService.getAllCustomers().stream()
                .map(userMapper::toDto).toList());
    }

    @GetMapping("/getAdmins")
    public ResponseEntity<List<UserDto>> getAllAdmins(Principal principal){
        Long adminId = userService.findByEmail(principal.getName()).get().getId();
        return ResponseEntity.ok(userService.getAllAdmins(adminId).stream()
                .map(userMapper::toDto).toList());
    }

    @GetMapping("/getCoaches")
    public ResponseEntity<List<CoachInfoDto>> getAllCoaches(){
        return ResponseEntity.ok(coachInfoService.getAll().stream()
                .map(coachInfoMapper::toDto).toList());
    }

    @GetMapping("/getCoachRecords/{coachId}")
    public ResponseEntity<List<TrainingForCoachDto>> getCoachRecords(@PathVariable Long coachId){
        return ResponseEntity.ok(userService.findById(coachId).get().getRecords().stream()
                .map(trainingMapper::toTrainingForCoachDto).toList());
    }

    @GetMapping("/getCustomerSubscriptions/{customerId}")
    @PreAuthorize("@adminChecker.checkIsUserCustomer(#customerId)")
    public ResponseEntity<List<UserSubscriptionDto>> getCustomerSubscriptions(@PathVariable Long customerId){
        return ResponseEntity.ok(userService.findById(customerId).get().getUserSubscriptions().stream()
                .map(userSubscriptionMapper::toDto).toList());
    }

    @GetMapping("/getCustomerTrainings/{customerId}")
    @PreAuthorize("@adminChecker.checkIsUserCustomer(#customerId)")
    public ResponseEntity<List<TrainingDto>> getCustomerTrainings(@PathVariable Long customerId){
        return ResponseEntity.ok(userService.findById(customerId).get().getTrainings().stream().
                map(trainingMapper::toDto).toList());
    }


    @PostMapping("/giveAdminRole/{userId}")
    @PreAuthorize("@adminChecker.checkForGivingAdminRole(#userId)")
    public ResponseEntity<Void> giveAdminRole(@PathVariable Long userId){
        adminService.giveAdminRole(userService.findById(userId).get());
        return ResponseEntity.ok().build();
    }


    @PostMapping("/takeAdminRole/{userId}")
    @PreAuthorize("@adminChecker.checkForTakingAdminRole(#userId, #principal.getName())")
    public ResponseEntity<Void> takeAdminRole(@PathVariable Long userId, Principal principal){
        adminService.takeAdminRole(userService.findById(userId).get());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/giveCoachRole/{userId}")
    @PreAuthorize("@adminChecker.checkForGivingCoachRole(#userId)")
    public ResponseEntity<Void> giveCoachRole(@PathVariable Long userId, @Valid @RequestBody CoachInfoCreationDto coachInfoCreationDto){
        adminService.giveCoachRole(userService.findById(userId).get(), coachInfoMapper.toEntity(coachInfoCreationDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/takeCoachRole/{coachId}/{coachToReplaceId}")
    @PreAuthorize("@adminChecker.checkForTakingCoachRole(#coachId, #coachToReplaceId)")
    public ResponseEntity<Void> takeCoachRole(@PathVariable Long coachId, @PathVariable Long coachToReplaceId){
        adminService.takeCoachRole(userService.findById(coachId).get(), userService.findById(coachToReplaceId).get());
        return ResponseEntity.ok().build();
    }
}
