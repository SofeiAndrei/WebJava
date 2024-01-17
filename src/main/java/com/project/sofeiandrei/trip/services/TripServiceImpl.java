package com.project.sofeiandrei.trip.services;

import com.project.sofeiandrei.expense.model.Expense;
import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.repositories.TripRepository;
import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("trip_service")
@Transactional
public class TripServiceImpl implements TripService {

  @Autowired
  TripRepository tripRepository;

  @Autowired
  UserRepository userRepository;

  @Override
  public Trip createTrip(Trip trip){
    User.getSignedInUser().addTrip(trip);

    return tripRepository.save(trip);
  }

  @Override
  public Trip findById(Long tripId) throws Exception {
    return tripRepository.findById(tripId).orElseThrow(() -> new Exception("Trip not found"));
  }

  @Override
  public List<Trip> findAllUserTrips(Long userId) throws Exception {
    Boolean userExists = userRepository.existsById(userId);

    if (!userExists) {
      throw new Exception("User does not exist");
    } else {
      return tripRepository.findAllUserTrips(userId);
    }
  }

  @Override
  public Trip updateTrip(Long tripId, Trip trip) throws Exception {
    Boolean tripExists = tripRepository.existsById(tripId);

    if (!tripExists) {
      throw new Exception("Trip not found");
    } else {
      return tripRepository.save(trip);
    }
  }

  @Override
  public String deleteTrip(Long tripId) throws Exception {
    Boolean tripExists = tripRepository.existsById(tripId);

    if (!tripExists) {
      throw new Exception("Trip not found");
    } else {
      tripRepository.deleteById(tripId);
      return "Trip deleted successfully";
    }
  }

  @Override
  public List<Expense> getAllTripExpenses(Long tripId) throws Exception {
    Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new Exception("Trip not found"));

    return trip.getExpenses();
  }
}
