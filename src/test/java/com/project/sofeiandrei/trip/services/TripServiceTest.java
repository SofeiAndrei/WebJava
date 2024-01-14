package com.project.sofeiandrei.trip.services;

import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.repositories.TripRepository;
import com.project.sofeiandrei.trip.services.TripService;
import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

public class TripServiceTest {
  @Mock
  private TripRepository tripRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private TripServiceImpl tripService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateTrip() throws Exception {
    Trip trip = new Trip();
    trip.setLocation("London");
    trip.setStartDate(new Date("08/10/2024"));
    trip.setEndDate(new Date("18/10/2024"));
    when(tripRepository.save(trip)).thenReturn(trip);

    Trip resultedTrip = tripService.createTrip(trip);

    assertNotNull(resultedTrip);
    verify(tripRepository, times(1)).save(trip);
  }

  @Test
  void testFindById() throws Exception {
    Trip trip = new Trip();
    trip.setLocation("London");
    trip.setStartDate(new Date("08/10/2024"));
    trip.setEndDate(new Date("18/10/2024"));
    when(tripRepository.findById(1L)).thenReturn(java.util.Optional.of(trip));

    Trip resultedTrip = tripService.findById(1L);

    assertNotNull(resultedTrip);
    verify(tripRepository, times(1)).findById(1L);
  }

  @Test
  void testFindByIdFailure() throws Exception {
    when(tripRepository.findById(1L)).thenReturn(java.util.Optional.empty());

    assertThrowsExactly(Exception.class, () -> {
      tripService.findById(1L);
    }, "Trip not found");

    verify(tripRepository, times(1)).findById(1L);
  }

  @Test
  void testFindAllUserTrips() throws Exception {
    User.signedInUser = new User();
    User.signedInUser.setUserId(1L);
    when(userRepository.existsById(1L)).thenReturn(true);

    Trip trip = new Trip();
    trip.setLocation("London");
    trip.setStartDate(new Date("08/10/2024"));
    trip.setEndDate(new Date("18/10/2024"));

    when(tripRepository.findAllUserTrips(1L)).thenReturn(java.util.List.of(trip));

    java.util.List<Trip> resultedTrips = tripService.findAllUserTrips(1L);

    assertNotNull(resultedTrips);
    verify(tripRepository, times(1)).findAllUserTrips(1L);
  }

  @Test
  void testUpdateTrip() throws Exception {
    Trip trip = new Trip();
    trip.setLocation("London");
    trip.setStartDate(new Date("08/10/2024"));
    trip.setEndDate(new Date("18/10/2024"));
    when(tripRepository.save(trip)).thenReturn(trip);
    when(tripRepository.existsById(1L)).thenReturn(true);

    Trip resultedTrip = tripService.updateTrip(1L, trip);

    assertNotNull(resultedTrip);
    verify(tripRepository, times(1)).save(trip);
  }

  @Test
  void testDeleteTripSuccess() throws Exception {
    Trip trip = new Trip();
    trip.setLocation("London");
    trip.setStartDate(new Date("08/10/2024"));
    trip.setEndDate(new Date("18/10/2024"));
    when(tripRepository.existsById(1L)).thenReturn(true);

    String resultedMessage = tripService.deleteTrip(1L);

    assertNotNull(resultedMessage);
    verify(tripRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDeleteTripFailure() throws Exception {
    when(tripRepository.existsById(1L)).thenReturn(false);

    assertThrowsExactly(Exception.class, () -> {
      tripService.deleteTrip(1L);
    }, "Trip not found");

    verify(tripRepository, times(1)).existsById(1L);
  }
}