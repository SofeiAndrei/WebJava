package com.project.sofeiandrei.trip.services;

import com.project.sofeiandrei.expense.model.Expense;
import com.project.sofeiandrei.trip.model.Trip;

import java.util.List;

public interface TripService {
  Trip createTrip(Trip trip) throws Exception;
  Trip findById(Long tripId) throws Exception;
  List<Trip> findAllUserTrips(Long userId) throws Exception;
  Trip updateTrip(Long tripId, Trip trip) throws Exception;
  String deleteTrip(Long tripId) throws Exception;
  List<Expense> getAllTripExpenses(Long tripId) throws Exception;
}
