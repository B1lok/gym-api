package com.example.gymapi.web.controller;


import com.example.gymapi.service.UserService;
import com.example.gymapi.web.dto.auth.AuthRequest;
import com.example.gymapi.web.dto.auth.AuthResponse;
import com.example.gymapi.web.dto.user.UserCreationDto;
import com.example.gymapi.web.mapper.AuthMapper;
import com.example.gymapi.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthMapper authMapper;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserCreationDto userCreationDto){
        userService.createUser(userMapper.toEntity(userCreationDto));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody AuthRequest authRequest){
        return ResponseEntity.of(userService.signIn(authRequest.getLogin(), authRequest.getPassword())
                .map(authMapper::toAuthResponse));
    }



}
