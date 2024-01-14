package com.project.sofeiandrei.trip.repositories;

import com.project.sofeiandrei.trip.model.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("trip_repository")
public interface TripRepository extends CrudRepository<Trip, Long> {
  @Query("SELECT t FROM Trip t JOIN t.users u WHERE u.id = :userId")
  List<Trip> findAllUserTrips(Long userId);
}
