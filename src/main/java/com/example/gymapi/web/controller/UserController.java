package com.example.gymapi.web.controller;


import com.example.gymapi.service.SubscriptionService;
import com.example.gymapi.service.UserService;
import com.example.gymapi.service.UserSubscriptionService;
import com.example.gymapi.web.dto.ExceptionResponse;
import com.example.gymapi.web.dto.subscription.SubscriptionDto;
import com.example.gymapi.web.dto.user.UserDto;
import com.example.gymapi.web.dto.user.UserUpdateDto;
import com.example.gymapi.web.dto.userSubscription.UserSubscriptionCreationDto;
import com.example.gymapi.web.mapper.SubscriptionMapper;
import com.example.gymapi.web.mapper.UserMapper;
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

    @PostMapping("/buySubscription/{subscriptionId}")
    public ResponseEntity<Void> buyUserSubscription(@PathVariable Long subscriptionId,
                                                    @RequestBody UserSubscriptionCreationDto userSubscriptionCreationDto,
                                                    Principal principal){
        userSubscriptionService.buySubscription(subscriptionService.getById(subscriptionId),
                principal.getName(),
                userSubscriptionCreationDto);
        return ResponseEntity.ok().build();
    }
}
