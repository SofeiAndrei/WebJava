package com.project.sofeiandrei.trip_invitation.controllers;

import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.repositories.TripRepository;
import com.project.sofeiandrei.trip_invitation.model.TripInvitation;
import com.project.sofeiandrei.trip_invitation.repositories.TripInvitationRepository;
import com.project.sofeiandrei.trip_invitation.services.TripInvitationService;
import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TripInvitationControllerTest {
  @Mock
  TripInvitationService tripInvitationService;

  @Mock
  TripRepository tripRepository;

  @Mock
  UserRepository userRepository;

  @Mock
  TripInvitationRepository tripInvitationRepository;

  @InjectMocks
  TripInvitationController tripInvitationController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void sendInvitation_Success() throws Exception {
    Long tripId = 1L;
    Long receiverId = 1L;

    User mockSender = new User();

    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(mockSender);
      when(User.getSignedInUser()).thenReturn(mockSender);
    };

    TripInvitation mockTripInvitation = new TripInvitation();
    mockTripInvitation.setSender(mockSender);
    when(tripInvitationRepository.findById(any())).thenReturn(Optional.of(mockTripInvitation));
    when(userRepository.findById(mockSender.getUserId())).thenReturn(Optional.of(mockSender));

    ResponseEntity<String> responseEntity = tripInvitationController.sendInvitation(tripId, receiverId);

    assertEquals(200, responseEntity.getStatusCode().value());
    verify(tripInvitationService, times(1)).sendInvitation(tripId, receiverId);
  }

  @Test
  void sendInvitation_Failure() throws Exception {
    Long tripId = 1L;
    Long receiverId = 1L;

    User mockSender = new User();

    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(mockSender);
      when(User.getSignedInUser()).thenReturn(mockSender);
    };

    when(tripRepository.findById(tripId)).thenReturn(Optional.empty());
    doThrow(new Exception("Trip not found")).when(tripInvitationService).sendInvitation(tripId, receiverId);

    ResponseEntity<String> responseEntity = tripInvitationController.sendInvitation(tripId, receiverId);

    assertEquals(400, responseEntity.getStatusCode().value());
    assertEquals("Trip not found", responseEntity.getBody());

    verify(tripInvitationService, times(1)).sendInvitation(tripId, receiverId);
  }

  @Test
  void sendInvitation_Unauthorized() throws Exception {
    Long tripId = 1L;
    Long receiverId = 1L;

    User mockSender = new User();

    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(mockSender);
      when(User.getSignedInUser()).thenReturn(mockSender);
    };

    when(tripRepository.findById(tripId)).thenReturn(Optional.of(new Trip()));
    doThrow(new Exception("User not logged in")).when(tripInvitationService).sendInvitation(tripId, receiverId);

    ResponseEntity<String> responseEntity = tripInvitationController.sendInvitation(tripId, receiverId);

    assertEquals(400, responseEntity.getStatusCode().value());
    assertEquals("User not logged in", responseEntity.getBody());

    verify(tripInvitationService, times(1)).sendInvitation(tripId, receiverId);
  }

  @Test
  void acceptInvitation_Success() throws Exception {
    Long tripId = 1L;

    when(tripRepository.findById(any())).thenReturn(null);
    when(tripInvitationRepository.findById(any())).thenReturn(Optional.of(new TripInvitation()));

    ResponseEntity<String> responseEntity = tripInvitationController.acceptInvitation(tripId);

    assertEquals(200, responseEntity.getStatusCode().value());
    verify(tripInvitationService, times(1)).acceptInvitation(tripId);
  }

  @Test
  void acceptInvitation_Failure() throws Exception {
    Long tripId = 1L;
    Long senderId = 1L;

    User mockReceiver = new User();
    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(mockReceiver);
      when(User.getSignedInUser()).thenReturn(mockReceiver);
    };

    doThrow(new Exception("Trip Invitation not found")).when(tripInvitationService).acceptInvitation(tripId);

    ResponseEntity<String> responseEntity = tripInvitationController.acceptInvitation(tripId);

    assertEquals(400, responseEntity.getStatusCode().value());
    assertEquals("Trip Invitation not found", responseEntity.getBody());

    verify(tripInvitationService, times(1)).acceptInvitation(tripId);
  }

  @Test
  void acceptInvitation_Unauthorized() throws Exception {
    Long tripId = 1L;
    TripInvitation tripInvitation = new TripInvitation();
    tripInvitation.setSender(null);
    doThrow(new Exception("User not authorized")).when(tripInvitationService).acceptInvitation(tripId);

    ResponseEntity<String> responseEntity = tripInvitationController.acceptInvitation(tripId);

    assertEquals(400, responseEntity.getStatusCode().value());
    assertEquals("User not authorized", responseEntity.getBody());
    verify(tripInvitationService, times(1)).acceptInvitation(tripId);
  }

  @Test
  void declineInvitation_Success() throws Exception {
    Long tripId = 1L;

    ResponseEntity<String> responseEntity = tripInvitationController.declineInvitation(tripId);

    assertEquals(200, responseEntity.getStatusCode().value());
    verify(tripInvitationService, times(1)).declineInvitation(tripId);
  }

  @Test
  void declineInvitation_Failure() throws Exception {
    Long tripId = 1L;

    TripInvitation mockTripInvitation = new TripInvitation();

    doThrow(new Exception("Trip Invitation not found")).when(tripInvitationService).declineInvitation(tripId);

    ResponseEntity<String> responseEntity = tripInvitationController.declineInvitation(tripId);

    assertEquals(400, responseEntity.getStatusCode().value());
    assertEquals("Trip Invitation not found", responseEntity.getBody());

    verify(tripInvitationService, times(1)).declineInvitation(tripId);
  }

  @Test
  void declineInvitation_Unauthorized() throws Exception {
    Long tripId = 1L;
    TripInvitation tripInvitation = new TripInvitation();
    tripInvitation.setReceiver(null);
    doThrow(new Exception("User not authorized")).when(tripInvitationService).declineInvitation(tripId);

    ResponseEntity<String> responseEntity = tripInvitationController.declineInvitation(tripId);

    assertEquals(400, responseEntity.getStatusCode().value());
    assertEquals("User not authorized", responseEntity.getBody());
    verify(tripInvitationService, times(1)).declineInvitation(tripId);
  }

  @Test
  void retractInvitation_Success() throws Exception {
    Long tripId = 1L;
    Long receiverId = 1L;

    ResponseEntity<String> responseEntity = tripInvitationController.retractInvitation(tripId, receiverId);

    assertEquals(200, responseEntity.getStatusCode().value());
    verify(tripInvitationService, times(1)).retractInvitation(tripId, receiverId);
  }

  @Test
  void retractInvitation_Failure() throws Exception {
    Long tripId = 1L;
    Long receiverId = 1L;

    TripInvitation mockTripInvitation = new TripInvitation();

    doThrow(new Exception("Trip Invitation not found")).when(tripInvitationService).retractInvitation(tripId, receiverId);

    ResponseEntity<String> responseEntity = tripInvitationController.retractInvitation(tripId, receiverId);

    assertEquals(400, responseEntity.getStatusCode().value());
    assertEquals("Trip Invitation not found", responseEntity.getBody());
    verify(tripInvitationService, times(1)).retractInvitation(tripId, receiverId);
  }

  @Test
  void retractInvitation_Unauthorized()throws Exception {
    Long tripId = 1L;
    Long receiverId = 1L;
    TripInvitation tripInvitation = new TripInvitation();
    tripInvitation.setSender(null);
    doThrow(new Exception("User not authorized")).when(tripInvitationService).retractInvitation(tripId, receiverId);

    ResponseEntity<String> responseEntity = tripInvitationController.retractInvitation(tripId, receiverId);

    assertEquals(400, responseEntity.getStatusCode().value());
    assertEquals("User not authorized", responseEntity.getBody());
    verify(tripInvitationService, times(1)).retractInvitation(tripId, receiverId);
  }
}
