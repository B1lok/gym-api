package com.example.gymapi.web.controller;


import com.example.gymapi.service.UserService;
import com.example.gymapi.web.dto.user.UserDto;
import com.example.gymapi.web.dto.user.UserUpdateDto;
import com.example.gymapi.web.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gym/user")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/self")
    public ResponseEntity<UserDto> getSelf(Principal principal) {
        return ResponseEntity.of(userService.findByEmail(principal.getName()).map(userMapper::toDto));
    }

    @PatchMapping("/self")
    public ResponseEntity<UserDto> updateSelf(Principal principal,@Valid @RequestBody UserUpdateDto updateDto) {
        return ResponseEntity.of(userService.findByEmail(principal.getName())
                .map(user -> userMapper.partialUpdate(updateDto, user))
                .map(userService::updateUser)
                .map(userMapper::toDto));
    }
}
