package com.project.sofeiandrei.trip.controllers;

import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.services.TripService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("trip_controller")
@RequestMapping("/api/trips")
public class TripController {

  @Autowired
  TripService tripService;

  @PostMapping("")
  public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) throws Exception {
    return ResponseEntity.ok(tripService.createTrip(trip));
  }

  @GetMapping("/{tripId}")
  public ResponseEntity<Trip> getTrip(HttpServletRequest request, @PathVariable("tripId") Long tripId) throws Exception {
    return ResponseEntity.ok(tripService.findById(tripId));
  }

  @GetMapping("")
  public ResponseEntity<List<Trip>> getAllTrips(HttpServletRequest request, @RequestParam Long userId) throws Exception {
    List<Trip> trips = tripService.findAllUserTrips(userId);
    return new ResponseEntity<>(trips, HttpStatus.OK);
  }

  @PutMapping("/{tripId}")
  public ResponseEntity<Trip> updateTrip(HttpServletRequest request, @PathVariable("tripId") Long tripId, @RequestBody Trip trip) throws Exception {
    return ResponseEntity.ok(tripService.updateTrip(tripId, trip));
  }

  @DeleteMapping("/{tripId}")
  public ResponseEntity<String> deleteTrip(HttpServletRequest request, @PathVariable("tripId") Long tripId) throws Exception {
    return ResponseEntity.ok(tripService.deleteTrip(tripId));
  }
}
