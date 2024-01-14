package com.project.sofeiandrei.trip_invitations.services;

import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.repositories.TripRepository;
import com.project.sofeiandrei.trip_invitations.model.TripInvitation;
import com.project.sofeiandrei.trip_invitations.repositories.TripInvitationRepository;
import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import com.project.sofeiandrei.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("trip_invitation_service")
public class TripInvitationServiceImpl implements TripInvitationService {

  @Autowired
  TripInvitationRepository tripInvitationRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  TripRepository tripRepository;

  @Override
  public void sendInvitation(Long tripId, Long userId) throws Exception {
    User sender = User.getSignedInUser();
    Optional<User> receiver = userRepository.findById(userId);
    Optional<Trip> trip = tripRepository.findById(tripId);

    if (sender == null) {
      throw new Exception("User not logged in");
    } else if (receiver.isEmpty()) {
      throw new Exception("Receiver User not found");
    } else if (trip.isEmpty()) {
      throw new Exception("Trip not found");
    } else {
      TripInvitation.TripInvitationId tripInvitationId = new TripInvitation.TripInvitationId();
      tripInvitationId.setReceiverId(userId);
      tripInvitationId.setTripId(tripId);

      TripInvitation tripInvitation = new TripInvitation();
      tripInvitation.setTripInvitationId(tripInvitationId);
      tripInvitation.setSender(sender);
      tripInvitation.setReceiver(receiver.get());
      tripInvitation.setTrip(trip.get());

      tripInvitationRepository.save(tripInvitation);
    }
  }

  @Override
  public void acceptInvitation(Long tripId) throws Exception {
    TripInvitation tripInvitation = checkConditionsAndReturnTripInvitation(tripId, User.getSignedInUser().getUserId());
    if(authorizeUser(tripInvitation.getSender().getUserId())) {
      throw new Exception("User not authorized");
    } else {
      User.getSignedInUser().addTrip(tripInvitation.getTrip());
      tripInvitationRepository.delete(tripInvitation);
    }

  }

  @Override
  public void declineInvitation(Long tripId) throws Exception {
    TripInvitation tripInvitation = checkConditionsAndReturnTripInvitation(tripId, User.getSignedInUser().getUserId());
    if(authorizeUser(tripInvitation.getReceiver().getUserId())) {
      throw new Exception("User not authorized");
    } else {
      tripInvitationRepository.delete(tripInvitation);
    }
  }

  @Override
  public void retractInvitation(Long tripId, Long userId) throws Exception {
    TripInvitation tripInvitation = checkConditionsAndReturnTripInvitation(tripId, userId);
    if(authorizeUser(tripInvitation.getSender().getUserId())) {
      throw new Exception("User not authorized");
    } else {
      tripInvitationRepository.delete(tripInvitation);
    }
  }

  private Optional<TripInvitation> findTripInvitation(Long receiverId, Long tripId) {
    TripInvitation.TripInvitationId tripInvitationId = new TripInvitation.TripInvitationId();
    tripInvitationId.setReceiverId(receiverId);
    tripInvitationId.setTripId(tripId);

    return tripInvitationRepository.findById(tripInvitationId);
  }

  private TripInvitation checkConditionsAndReturnTripInvitation(Long tripId, Long userId) throws Exception {
    if (User.getSignedInUser() == null) {
      throw new Exception("User not logged in");
    } else {
      Optional<TripInvitation> tripInvitation = findTripInvitation(userId, tripId);
      if (tripInvitation.isEmpty()) {
        throw new Exception("Trip Invitation not found");
      }
      return tripInvitation.get();
    }
  }

  private Boolean authorizeUser(Long userId) {
    return User.getSignedInUser().getUserId().equals(userId);
  }
}
