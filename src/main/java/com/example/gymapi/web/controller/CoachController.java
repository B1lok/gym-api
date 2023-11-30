package com.example.gymapi.web.controller;


import com.example.gymapi.domain.User;
import com.example.gymapi.service.CoachInfoService;
import com.example.gymapi.service.TrainingService;
import com.example.gymapi.service.UserService;
import com.example.gymapi.web.dto.coach.CoachInfoDto;
import com.example.gymapi.web.dto.coach.CoachInfoUpdateDto;
import com.example.gymapi.web.dto.training.TrainingForCoachDto;
import com.example.gymapi.web.mapper.CoachInfoMapper;
import com.example.gymapi.web.mapper.TrainingMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Tag(name = "Coach controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/gym/coach")
public class CoachController {

    private final CoachInfoMapper coachInfoMapper;

    private final CoachInfoService coachInfoService;

    private final UserService userService;

    private final TrainingMapper trainingMapper;

    private final TrainingService trainingService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CoachInfoDto>> getAllCoaches() {
        return ResponseEntity.ok(coachInfoService.getAll().stream()
                .map(coachInfoMapper::toDto).toList());
    }

    @GetMapping("/getSelf")
    public ResponseEntity<CoachInfoUpdateDto> getCoachInfo(Principal principal){
        var coachInfo = coachInfoService.getByCoachId(userService.findByEmail(principal.getName()).get().getId()).get();
        return ResponseEntity.ok(coachInfoMapper.toDtoForCoach(coachInfo));
    }

    @GetMapping("/records/getMyRecords")
    public ResponseEntity<List<TrainingForCoachDto>> getAllMyRecords(Principal principal) {
        return ResponseEntity.of(userService.findByEmail(principal.getName())
                .map(User::getRecords)
                .map(records -> records.stream()
                        .map(trainingMapper::toTrainingForCoachDto).toList()));
    }

    @PostMapping("/records/cancelById/{recordId}")
    @PreAuthorize("@trainingChecker.checkForCoachCancel(#principal.getName(), #recordId)")
    public ResponseEntity<Void> cancelRecordById(Principal principal, @PathVariable Long recordId) {
        var record = trainingService.findById(recordId).get();
        trainingService.cancelTraining(record, record.getUserSubscription());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<CoachInfoDto> updateProfessionalInformation(@Valid @RequestBody CoachInfoUpdateDto coachInfoUpdateDto,
                                                              Principal principal){
        var coachInfo = coachInfoService.getByCoachId(userService.findByEmail(principal.getName()).get().getId()).get();
        var updatedCoachInfo = coachInfoService.updateCoachInfo(coachInfoMapper.partialUpdate(coachInfoUpdateDto, coachInfo));
        return ResponseEntity.ok(coachInfoMapper.toDto(updatedCoachInfo));
    }

}