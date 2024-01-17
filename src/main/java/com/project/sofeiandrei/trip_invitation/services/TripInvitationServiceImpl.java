package com.project.sofeiandrei.trip_invitation.services;

import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.repositories.TripRepository;
import com.project.sofeiandrei.trip_invitation.model.TripInvitation;
import com.project.sofeiandrei.trip_invitation.repositories.TripInvitationRepository;
import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("trip_invitation_service")
@Transactional
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
    User receiver = userRepository.findById(userId).orElseThrow(() -> new Exception("Receiver User not found"));
    Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new Exception("Trip not found"));

    if (sender == null) {
      throw new Exception("User not logged in");
    } else {
      TripInvitation.TripInvitationId tripInvitationId = new TripInvitation.TripInvitationId();
      tripInvitationId.setReceiverId(userId);
      tripInvitationId.setTripId(tripId);

      TripInvitation tripInvitation = new TripInvitation();
      tripInvitation.setTripInvitationId(tripInvitationId);
      tripInvitation.setSender(sender);
      tripInvitation.setReceiver(receiver);
      tripInvitation.setTrip(trip);

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
    if (User.signedInUser == null) {
      throw new Exception("User not logged in");
    } else {
      TripInvitation tripInvitation = findTripInvitation(userId, tripId).orElseThrow(() -> new Exception("Trip Invitation not found"));
      return tripInvitation;
    }
  }

  private Boolean authorizeUser(Long userId) {
    return User.getSignedInUser().getUserId().equals(userId);
  }
}
