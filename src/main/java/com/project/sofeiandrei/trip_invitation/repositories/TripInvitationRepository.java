package com.project.sofeiandrei.trip_invitation.repositories;

import com.project.sofeiandrei.trip_invitation.model.TripInvitation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("trip_invitation_repository")
public interface TripInvitationRepository extends CrudRepository<TripInvitation, TripInvitation.TripInvitationId> {
}
