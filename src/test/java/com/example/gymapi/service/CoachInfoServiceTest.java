package com.example.gymapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.gymapi.data.CoachInfoRepository;
import com.example.gymapi.domain.CoachInfo;
import com.example.gymapi.domain.GymZone;
import com.example.gymapi.domain.User;
import com.example.gymapi.service.impl.CoachInfoServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = CoachInfoServiceImpl.class)
public class CoachInfoServiceTest {
  @MockBean
  CoachInfoRepository coachInfoRepository;
  @Autowired
  CoachInfoService coachInfoService;

  private User mockCoach;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    // Create a mock User object to be used in the tests
    mockCoach = new User();
    mockCoach.setId(1L);
    mockCoach.setFirstName("John");
  }

  @Test
  void testGetAll() {
    // Arrange
    CoachInfo coach1 = new CoachInfo();
    coach1.setId(1L);
    coach1.setCoach(mockCoach);
    coach1.setEducation("B.Sc. in Sports Science");
    coach1.setExperience("5 years");
    coach1.setSpecialization(GymZone.GYM);

    CoachInfo coach2 = new CoachInfo();
    coach2.setId(2L);
    coach2.setCoach(mockCoach);
    coach2.setEducation("M.Sc. in Physical Education");
    coach2.setExperience("8 years");
    coach2.setSpecialization(GymZone.SWIMMING_POOL);

    when(coachInfoRepository.findAll()).thenReturn(List.of(coach1, coach2));

    // Act
    List<CoachInfo> result = coachInfoService.getAll();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    verify(coachInfoRepository, times(1)).findAll();
  }

  @Test
  void testGetByCoachId() {
    // Arrange
    CoachInfo coach = new CoachInfo();
    coach.setId(1L);
    coach.setCoach(mockCoach);
    coach.setEducation("B.Sc. in Sports Science");
    coach.setExperience("5 years");
    coach.setSpecialization(GymZone.GYM);

    when(coachInfoRepository.findByCoachId(1L)).thenReturn(Optional.of(coach));

    // Act
    Optional<CoachInfo> result = coachInfoService.getByCoachId(1L);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getCoach().getId());
    assertEquals(GymZone.GYM, result.get().getSpecialization());
    verify(coachInfoRepository, times(1)).findByCoachId(1L);
  }

  @Test
  void testUpdateCoachInfo() {
    // Arrange
    CoachInfo coach = new CoachInfo();
    coach.setId(1L);
    coach.setCoach(mockCoach);
    coach.setEducation("B.Sc. in Sports Science");
    coach.setExperience("6 years");
    coach.setSpecialization(GymZone.GYM);

    when(coachInfoRepository.save(coach)).thenReturn(coach);

    // Act
    CoachInfo result = coachInfoService.updateCoachInfo(coach);

    // Assert
    assertNotNull(result);
    assertEquals("6 years", result.getExperience());
    verify(coachInfoRepository, times(1)).save(coach);
  }
}
