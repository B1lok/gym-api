package com.example.gymapi.web.controller;

import com.example.gymapi.service.SubscriptionService;
import com.example.gymapi.web.dto.subscription.SubscriptionCreationDto;
import com.example.gymapi.web.dto.subscription.SubscriptionDto;
import com.example.gymapi.web.dto.subscription.SubscriptionUpdateDto;
import com.example.gymapi.web.mapper.SubscriptionMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Subscription controller")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/gym/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    private final SubscriptionMapper subscriptionMapper;

    @Operation(summary = "Get all possible subscriptions")
    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.findAll().stream()
                .map(subscriptionMapper::toDto).toList());
    }

    @PostMapping("/createSubscription")
    public ResponseEntity<SubscriptionDto> createSubscription(@Valid @RequestBody SubscriptionCreationDto subscriptionCreationDto) {
        var created = subscriptionService.createOne(subscriptionMapper.toEntity(subscriptionCreationDto));
        return new ResponseEntity<>(subscriptionMapper.toDto(created), HttpStatus.CREATED);
    }

    @PatchMapping("/updateSubscription/{id}")
    public ResponseEntity<SubscriptionDto> updateSubscription(@PathVariable Long id,
                                                              @Valid @RequestBody SubscriptionUpdateDto subscriptionUpdateDto) {
        var updated = subscriptionService.updateOne(subscriptionMapper.partialUpdate(subscriptionUpdateDto, subscriptionService.getById(id)));
        return new ResponseEntity<>(subscriptionMapper.toDto(updated), HttpStatus.OK);
    }

    @DeleteMapping("/deleteSubscription/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id){
        subscriptionService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }


}
