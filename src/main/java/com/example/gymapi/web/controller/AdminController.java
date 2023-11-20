package com.example.gymapi.web.controller;


import com.example.gymapi.service.AdminService;
import com.example.gymapi.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Admin Controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/gym/admin")
public class AdminController {

    private final UserService userService;

    private final AdminService adminService;

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
    public ResponseEntity<Void> giveCoachRole(@PathVariable Long userId){
        adminService.giveCoachRole(userService.findById(userId).get());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/takeCoachRole/{userId}")
    @PreAuthorize("@adminChecker.checkForTakingCoachRole(#userId)")
    public ResponseEntity<Void> takeCoachRole(@PathVariable Long userId){
        adminService.takeCoachRole(userService.findById(userId).get());
        return ResponseEntity.ok().build();
    }
}
