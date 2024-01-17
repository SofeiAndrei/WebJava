package com.project.sofeiandrei.trip_invitation.services;

public interface TripInvitationService {
  public void sendInvitation(Long tripId, Long userId) throws Exception;
  public void acceptInvitation(Long tripId) throws Exception;
  public void declineInvitation(Long tripId) throws Exception;
  public void retractInvitation(Long tripId, Long userId) throws Exception;
}
