package com.example.gymapi.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.gymapi.data.UserSubscriptionRepository;
import com.example.gymapi.domain.CoachInfo;
import com.example.gymapi.domain.GymZone;
import com.example.gymapi.domain.Role;
import com.example.gymapi.domain.Subscription;
import com.example.gymapi.domain.User;
import com.example.gymapi.domain.UserSubscription;
import com.example.gymapi.exception.CoachNotFoundException;
import com.example.gymapi.exception.SubscriptionNotAllowedException;
import com.example.gymapi.service.impl.UserSubscriptionServiceImpl;
import com.example.gymapi.web.dto.userSubscription.UserSubscriptionCreationDto;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = UserSubscriptionServiceImpl.class)
public class UserSubscriptionServiceTest {

    @Autowired
    private UserSubscriptionService userSubscriptionService;

    @MockBean
    private UserSubscriptionRepository userSubscriptionRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private CoachInfoService coachInfoService;

    private User mockUser;
    private Subscription mockSubscription;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock User
        mockUser = new User();
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setEmail("john.doe@example.com");
        mockUser.setPhoneNumber("+38(067)123-45-67");
        mockUser.setRoles(Set.of(Role.USER));

        // Mock Subscription
        mockSubscription = new Subscription();
        mockSubscription.setSubscriptionType(GymZone.GYM);
        mockSubscription.setWithCoach(true);
        mockSubscription.setDurationInDays(30);
        mockSubscription.setVisitsAmount(10);
    }

    @Test
    void testBuySubscriptionSuccess() {
        UserSubscriptionCreationDto dto = new UserSubscriptionCreationDto();
        dto.setPurchaseDate(LocalDate.now());
        dto.setCoachId(1L);

        CoachInfo mockCoachInfo = new CoachInfo();
        mockCoachInfo.setSpecialization(GymZone.GYM);
        User mockCoach = new User();
        mockCoachInfo.setCoach(mockCoach);

        when(userService.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(coachInfoService.getByCoachId(dto.getCoachId())).thenReturn(
                Optional.of(mockCoachInfo));
        when(userSubscriptionRepository.save(any(UserSubscription.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        assertDoesNotThrow(
                () -> userSubscriptionService.buySubscription(mockSubscription, mockUser.getEmail(),
                        dto));
        verify(userSubscriptionRepository, times(1)).save(any(UserSubscription.class));
    }

    @Test
    void testBuySubscriptionWithInvalidCoach() {
        UserSubscriptionCreationDto dto = new UserSubscriptionCreationDto();
        dto.setPurchaseDate(LocalDate.now());
        dto.setCoachId(1L);

        when(userService.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(coachInfoService.getByCoachId(dto.getCoachId())).thenReturn(Optional.empty());

        CoachNotFoundException exception = assertThrows(CoachNotFoundException.class, () ->
                userSubscriptionService.buySubscription(mockSubscription, mockUser.getEmail(),
                        dto));

        assertEquals("Coach not found", exception.getMessage());
    }

    @Test
    void testBuySubscriptionAlreadyExists() {
        UserSubscriptionCreationDto dto = new UserSubscriptionCreationDto();
        dto.setPurchaseDate(LocalDate.now());

        UserSubscription existingSubscription = new UserSubscription();
        existingSubscription.setSubscriptionType(GymZone.GYM);
        existingSubscription.setExpirationDate(LocalDate.now().plusDays(10));
        existingSubscription.setTrainingsLeft(5);

        mockUser.setUserSubscriptions(Set.of(existingSubscription));

        when(userService.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        SubscriptionNotAllowedException exception =
                assertThrows(SubscriptionNotAllowedException.class, () ->
                        userSubscriptionService.buySubscription(mockSubscription,
                                mockUser.getEmail(), dto));

        assertEquals("You already have this type of subscription", exception.getMessage());
    }

    @Test
    void testBuySubscriptionWithoutCoach() {
        mockSubscription.setWithCoach(false);
        UserSubscriptionCreationDto dto = new UserSubscriptionCreationDto();
        dto.setPurchaseDate(LocalDate.now());

        when(userService.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(userSubscriptionRepository.save(any(UserSubscription.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        assertDoesNotThrow(
                () -> userSubscriptionService.buySubscription(mockSubscription, mockUser.getEmail(),
                        dto));
        verify(userSubscriptionRepository, times(1)).save(any(UserSubscription.class));
    }
}
