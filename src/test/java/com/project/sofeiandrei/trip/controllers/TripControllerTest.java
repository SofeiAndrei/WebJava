package com.project.sofeiandrei.trip.controllers;

import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.services.TripService;
import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.services.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TripControllerTest {
  @Mock
  private UserService userService;

  @Mock
  private TripService tripService;


  @InjectMocks
  private TripController tripController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateTripSuccess() throws Exception {
    User.signedInUser = new User();
    ResponseEntity<Trip> responseEntity = tripController.createTrip(new Trip());
    assertEquals(200, responseEntity.getStatusCode().value());
  }

  @Test
  void testGetTripSuccess() throws Exception {
    User.signedInUser = new User();
    ResponseEntity<Trip> responseEntity = tripController.getTrip(null, 0L);
    assertEquals(200, responseEntity.getStatusCode().value());
    verify(tripService, times(1)).findById(any());
  }

  @Test
  void testGetTripFailure() throws Exception {
    when(tripController.getTrip(null, any())).thenThrow(new Exception("Trip not found"));

    assertThrowsExactly(Exception.class, () -> {
      tripController.getTrip(null, 0L);
    }, "Trip not found");
  }

  @Test
  void testGetAllTripsSuccess() throws Exception {
    User.signedInUser = new User();

    HttpServletRequest request = mock(HttpServletRequest.class);

    ResponseEntity<List<Trip>> responseEntity = tripController.getAllTrips(request, (Long) 0L);
    assertEquals(200, responseEntity.getStatusCode().value());
    verify(tripService, times(1)).findAllUserTrips(any());
  }

  @Test
  void testGetAllTripsFailure() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);

    when(tripController.getAllTrips(request, (Long) 0L)).thenThrow(new Exception("User does not exist"));

    assertThrowsExactly(Exception.class, () -> {
      tripController.getAllTrips(request, (Long) 0L);
    }, "User does not exist");
  }

  @Test
  void testUpdateTripSuccess() throws Exception {
    User.signedInUser = new User();
    ResponseEntity<Trip> responseEntity = tripController.updateTrip(null, 0L, new Trip());
    assertEquals(200, responseEntity.getStatusCode().value());
    verify(tripService, times(1)).updateTrip(any(), any());
  }

  @Test
  void testUpdateTripFailure() throws Exception {
    when(tripController.updateTrip(null, any(), any())).thenThrow(new Exception("Trip not found"));

    assertThrowsExactly(Exception.class, () -> {
      tripController.updateTrip(null, 0L, new Trip());
    }, "Trip not found");
  }

  @Test
  void testDeleteTripSuccess() throws Exception {
    User.signedInUser = new User();
    ResponseEntity<String> responseEntity = tripController.deleteTrip(null, 0L);
    assertEquals(200, responseEntity.getStatusCode().value());
    verify(tripService, times(1)).deleteTrip(any());
  }

  @Test
  void testDeleteTripFailure() throws Exception {
    when(tripController.deleteTrip(null, any())).thenThrow(new Exception("Trip not found"));

    assertThrowsExactly(Exception.class, () -> {
      tripController.deleteTrip(null, 0L);
    }, "Trip not found");
  }
}
