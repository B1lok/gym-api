package com.example.gymapi.web.controller;


import com.example.gymapi.domain.User;
import com.example.gymapi.service.SubscriptionService;
import com.example.gymapi.service.TrainingService;
import com.example.gymapi.service.UserService;
import com.example.gymapi.service.UserSubscriptionService;
import com.example.gymapi.web.dto.ExceptionResponse;
import com.example.gymapi.web.dto.training.TrainingCreationDto;
import com.example.gymapi.web.dto.training.TrainingDto;
import com.example.gymapi.web.dto.user.UserDto;
import com.example.gymapi.web.dto.user.UserUpdateDto;
import com.example.gymapi.web.dto.userSubscription.UserSubscriptionCreationDto;
import com.example.gymapi.web.dto.userSubscription.UserSubscriptionDto;
import com.example.gymapi.web.mapper.TrainingMapper;
import com.example.gymapi.web.mapper.UserMapper;
import com.example.gymapi.web.mapper.UserSubscriptionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "User Controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/gym/user")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final UserSubscriptionService userSubscriptionService;

    private final SubscriptionService subscriptionService;

    private final UserSubscriptionMapper userSubscriptionMapper;

    private final TrainingService trainingService;

    private final TrainingMapper trainingMapper;

    @Operation(summary = "Get user information", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "403",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/self")
    public ResponseEntity<UserDto> getSelf(Principal principal) {
        return ResponseEntity.of(userService.findByEmail(principal.getName()).map(userMapper::toDto));
    }

    @Operation(summary = "Update user information", responses = {
            @ApiResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "403",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping("/self")
    public ResponseEntity<UserDto> updateSelf(Principal principal, @Valid @RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.of(userService.findByEmail(principal.getName())
                .map(user -> userMapper.partialUpdate(updateDto, user))
                .map(userService::updateUser)
                .map(userMapper::toDto));
    }

    @GetMapping("/mySubscriptions")
    @PreAuthorize("@roleChecker.checkForAllowedRoles(#principal.getName())")
    public ResponseEntity<List<UserSubscriptionDto>> getMySubscriptions(Principal principal) {
        return ResponseEntity.of(userService.findByEmail(principal.getName())
                .map(User::getUserSubscriptions)
                .map(subscriptions -> subscriptions.stream()
                        .map(userSubscriptionMapper::toDto).toList()));
    }

    @PostMapping("/buySubscription/{subscriptionId}")
    @PreAuthorize("@roleChecker.checkForAllowedRoles(#principal.getName())")
    public ResponseEntity<Void> buyUserSubscription(@PathVariable Long subscriptionId,
                                                    @Valid @RequestBody UserSubscriptionCreationDto userSubscriptionCreationDto,
                                                    Principal principal) {
        userSubscriptionService.buySubscription(subscriptionService.getById(subscriptionId),
                principal.getName(),
                userSubscriptionCreationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myTrainings")
    @PreAuthorize("@roleChecker.checkForAllowedRoles(#principal.getName())")
    public ResponseEntity<List<TrainingDto>> getMyTrainings(Principal principal) {
        return ResponseEntity.of(userService.findByEmail(principal.getName())
                .map(User::getTrainings)
                .map(trainings -> trainings.stream()
                        .map(trainingMapper::toDto).toList()));
    }

    @PostMapping("/training/enroll/{userSubscriptionId}/{coachId}")
    @PreAuthorize("@trainingChecker.checkForEnroll(#principal.getName(), #userSubscriptionId, #coachId)")
    public ResponseEntity<Void> enrollForTraining(@PathVariable Long userSubscriptionId,
                                                  @PathVariable Long coachId,
                                                  @Valid @RequestBody TrainingCreationDto trainingCreationDto,
                                                  Principal principal) {
        trainingService.createTraining(userService.findByEmail(principal.getName()).get(),
                trainingMapper.toEntity(trainingCreationDto),
                userSubscriptionService.findById(userSubscriptionId).get(),
                userService.findById(coachId).get());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/training/cancel/{trainingId}")
    @PreAuthorize("@trainingChecker.checkForCancel(#principal.getName(),#trainingId)")
    public ResponseEntity<Void> cancelTraining(@PathVariable Long trainingId, Principal principal) {
        var training = trainingService.findById(trainingId).get();
        trainingService.cancelTraining(training, training.getUserSubscription());
        return ResponseEntity.ok().build();
    }
}