package com.example.gymapi.data;

import com.example.gymapi.domain.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findAllByUserSubscriptionIdAndCoachNotNull(Long id);
}
