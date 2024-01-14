package com.project.sofeiandrei.trip_invitations.services;

import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.repositories.TripRepository;
import com.project.sofeiandrei.trip_invitations.model.TripInvitation;
import com.project.sofeiandrei.trip_invitations.repositories.TripInvitationRepository;
import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

public class TripInvitationServiceTest {
  @Mock
  private TripInvitationRepository tripInvitationRepository;

  @Mock
  private TripRepository tripRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private TripInvitationServiceImpl tripInvitationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSendInvitation() throws Exception {
    User mockSender = new User();

    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(mockSender);
      when(User.getSignedInUser()).thenReturn(mockSender);
    };

    User mockReceiver = new User();
    when(userRepository.findById(1L)).thenReturn(Optional.of(mockReceiver));

    Trip mockTrip = new Trip();
    when(tripRepository.findById(1L)).thenReturn(Optional.of(mockTrip));

    tripInvitationService.sendInvitation(1L, 1L);

    verify(tripInvitationRepository, times(1)).save(any(TripInvitation.class));
  }

  @Test
  void testAcceptInvitation() throws Exception {
    User mockReceiver = new User();

    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(mockReceiver);
      when(User.getSignedInUser()).thenReturn(mockReceiver);

      //Mocking Authorization
      when(User.getSignedInUser().getUserId()).thenReturn(mockReceiver.getUserId());
    };

    TripInvitation mockTripInvitation = new TripInvitation();
    mockTripInvitation.setReceiver(mockReceiver);
    mockTripInvitation.setSender(new User());
    mockTripInvitation.setTrip(new Trip());
    when(tripInvitationRepository.findById(any())).thenReturn(Optional.of(mockTripInvitation));

    tripInvitationService.acceptInvitation(1L);

    verify(tripInvitationRepository, times(1)).delete(any(TripInvitation.class));
  }

  @Test
  void testDeclineInvitation() throws Exception {
    User mockReceiver = new User();

    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(mockReceiver);
      when(User.getSignedInUser()).thenReturn(mockReceiver);

      //Mocking Authorization
      when(User.getSignedInUser().getUserId()).thenReturn(mockReceiver.getUserId());
    };

    TripInvitation mockTripInvitation = new TripInvitation();
    mockTripInvitation.setReceiver(mockReceiver);
    mockTripInvitation.setSender(new User());
    mockTripInvitation.setTrip(new Trip());
    when(tripInvitationRepository.findById(any())).thenReturn(Optional.of(mockTripInvitation));

    tripInvitationService.declineInvitation(1L);

    verify(tripInvitationRepository, times(1)).delete(any(TripInvitation.class));
  }

  @Test
  void testRetractInvitation() throws Exception {
    User mockSender = new User();

    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(mockSender);
      when(User.getSignedInUser()).thenReturn(mockSender);

      //Mocking Authorization
      when(User.getSignedInUser().getUserId()).thenReturn(mockSender.getUserId());
    };

    TripInvitation mockTripInvitation = new TripInvitation();
    mockTripInvitation.setReceiver(new User());
    mockTripInvitation.setSender(mockSender);
    mockTripInvitation.setTrip(new Trip());
    when(tripInvitationRepository.findById(any())).thenReturn(Optional.of(mockTripInvitation));

    tripInvitationService.retractInvitation(1L, 1L);

    verify(tripInvitationRepository, times(1)).delete(any(TripInvitation.class));
  }
}
