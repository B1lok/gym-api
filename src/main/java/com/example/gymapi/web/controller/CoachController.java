package com.example.gymapi.web.controller;

import com.example.gymapi.data.CoachInfoRepository;
import com.example.gymapi.service.CoachInfoService;
import com.example.gymapi.web.dto.coach.CoachInfoDto;
import com.example.gymapi.web.mapper.CoachInfoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Coach controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/gym/coach")
public class CoachController {

    private final CoachInfoMapper coachInfoMapper;

    private final CoachInfoService coachInfoService;
    @GetMapping("/getAll")
    public ResponseEntity<List<CoachInfoDto>> getAllCoaches(){
        return ResponseEntity.ok(coachInfoService.getAll().stream()
                .map(coachInfoMapper::toDto).toList());
    }

}
