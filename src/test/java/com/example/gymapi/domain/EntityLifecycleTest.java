package com.example.gymapi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.gymapi.config.TestContextInitializer;
import com.example.gymapi.data.CoachInfoRepository;
import com.example.gymapi.data.SubscriptionRepository;
import com.example.gymapi.data.TrainingRepository;
import com.example.gymapi.data.UserRepository;
import com.example.gymapi.data.UserSubscriptionRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {TestContextInitializer.class})
@Sql(scripts = {"/sql/insert-users.sql", "/sql/insert-user-roles.sql",
    "/sql/insert-subscriptions.sql", "/sql/insert-coach-info.sql", "/sql/insert-user-subscription.sql", "/sql/insert-trainings.sql"},
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class EntityLifecycleTest {

  @Autowired
  CoachInfoRepository coachInfoRepository;
  @Autowired
  SubscriptionRepository subscriptionRepository;
  @Autowired
  TrainingRepository trainingRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  UserSubscriptionRepository userSubscriptionRepository;

  @Test
  public void testFindUserById() {
    Optional<User> user = userRepository.findById(1L);
    assertThat(user).isPresent();
    assertThat(user.get().getEmail()).isEqualTo("john.doe@example.com");
  }

  @Test
  public void testUserRolesAssociation() {
    User user = userRepository.findById(4L).orElseThrow();
    assertThat(user.getRoles()).contains(Role.ADMIN, Role.USER);
  }

  @Test
  public void testSubscriptionDetails() {
    Subscription subscription = subscriptionRepository.findById(1L).orElseThrow();
    assertThat(subscription.getDurationInDays()).isEqualTo(30);
    assertThat(subscription.getPrice()).isEqualByComparingTo("50.00");
    assertThat(subscription.getSubscriptionType()).isEqualTo(GymZone.GYM);
  }

  @Test
  public void testUserSubscriptionRelationship() {
    UserSubscription userSubscription = userSubscriptionRepository.findById(2L).orElseThrow();
    assertThat(userSubscription.getUser().getId()).isEqualTo(2L);
    assertThat(userSubscription.getCoach().getId()).isEqualTo(3L);
    assertThat(userSubscription.getTrainingsLeft()).isEqualTo(5);
  }

  @Test
  public void testCoachInfoAssociation() {
    CoachInfo coachInfo = coachInfoRepository.findById(1L).orElseThrow();
    assertThat(coachInfo.getSpecialization()).isEqualTo(GymZone.GYM);
    assertThat(coachInfo.getCoach().getId()).isEqualTo(3L);
    assertThat(coachInfo.getEducation()).isEqualTo("Bachelor of Sports Science");
  }

  @Test
  public void testTrainingDetails() {
    Training training = trainingRepository.findById(1L).orElseThrow();
    assertThat(training.getTrainingType()).isEqualTo(GymZone.GYM);
    assertThat(training.getTrainingStatus()).isEqualTo(TrainingStatus.ACTIVE);
    assertThat(training.getTrainingStart()).isEqualTo("09:00:00");
    assertThat(training.getTrainingEnd()).isEqualTo("10:00:00");
    assertThat(training.getCoach().getId()).isEqualTo(3L);
    assertThat(training.getUser().getId()).isEqualTo(2L);
  }

  @Test
  public void testUserWithNoCoachSubscription() {
    UserSubscription userSubscription = userSubscriptionRepository.findById(1L).orElseThrow();
    assertThat(userSubscription.getCoach()).isNull();
  }

  @Test
  public void testUserWithCoachSubscription() {
    UserSubscription userSubscription = userSubscriptionRepository.findById(2L).orElseThrow();
    assertThat(userSubscription.getCoach().getId()).isEqualTo(3L);
  }

  @Test
  public void testUserTrainingAssociation() {
    Training training = trainingRepository.findById(1L).orElseThrow();
    assertThat(training.getUser().getId()).isEqualTo(2L);
  }


}
