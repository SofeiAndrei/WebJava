package com.project.sofeiandrei.trip_invitation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import com.project.sofeiandrei.trip_invitation.services.TripInvitationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("trip_invitation_controller")
@RequestMapping("/api/trip_invitations")
public class TripInvitationController {

  @Autowired
  TripInvitationService tripInvitationService;

  @PostMapping("/send_invitation")
  public ResponseEntity<String> sendInvitation(@RequestParam Long tripId, @RequestParam Long receiverId) throws Exception {
    try {
      tripInvitationService.sendInvitation(tripId, receiverId);
      return ResponseEntity.ok("Invitation sent successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/accept_invitation")
  public ResponseEntity<String> acceptInvitation(@RequestParam Long tripId) throws Exception {
    try {
      tripInvitationService.acceptInvitation(tripId);
      return ResponseEntity.ok("Invitation accepted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/decline_invitation")
  public ResponseEntity<String> declineInvitation(@RequestParam Long tripId) throws Exception {
    try {
      tripInvitationService.declineInvitation(tripId);
      return ResponseEntity.ok("Invitation declined successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/retract_invitation")
  public ResponseEntity<String> retractInvitation(@RequestParam Long tripId, @RequestParam Long receiverId) throws Exception {
    try {
      tripInvitationService.retractInvitation(tripId, receiverId);
      return ResponseEntity.ok("Invitation retracted successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
