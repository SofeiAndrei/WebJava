package com.project.sofeiandrei.trip.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.sofeiandrei.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trips")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "tripId" )
public class Trip implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  @Getter
  @Setter
  private Long tripId;

  @Getter
  @Setter
  @Column(nullable = false)
  private String location;

  @Getter
  @Setter
  @Column(nullable = false)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date startDate;

  @Getter
  @Setter
  @Column(nullable = false)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date endDate;

  @Getter
  @Setter
  @ManyToMany
  @JoinTable(name = "trip_user", schema = "public",
          joinColumns = {@JoinColumn(name = "trip_id")},
          inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private Set<User> users = new HashSet<User>();
}
