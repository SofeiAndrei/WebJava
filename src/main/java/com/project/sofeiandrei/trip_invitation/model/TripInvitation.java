package com.project.sofeiandrei.trip_invitation.model;

import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "trip_invitations")
public class TripInvitation {
  @EmbeddedId
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  @Getter
  @Setter
  private TripInvitationId tripInvitationId;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  @Getter
  @Setter
  @NotBlank(message = "Sender is mandatory")
  private User sender;

  @ManyToOne
  @MapsId("receiverId")
  @JoinColumn(name = "receiver_id")
  @Getter
  @Setter
  @NotBlank(message = "Receiver is mandatory")
  private User receiver;

  @ManyToOne
  @MapsId("tripId")
  @JoinColumn(name = "trip_id")
  @Getter
  @Setter
  @NotBlank(message = "Trip is mandatory")
  private Trip trip;

  @Embeddable
  public static class TripInvitationId implements Serializable {
    @Column(name = "receiver_id")
    @Getter
    @Setter
    private Long receiverId;
    @Column(name = "trip_id")
    @Getter
    @Setter
    private Long tripId;
  }
}

