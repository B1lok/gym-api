package com.example.gymapi.data;

import com.example.gymapi.domain.CoachInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoachInfoRepository extends JpaRepository<CoachInfo, Long> {

    Optional<CoachInfo> findByCoachId(Long id);
}
