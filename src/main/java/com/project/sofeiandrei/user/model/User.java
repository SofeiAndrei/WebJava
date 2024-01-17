package com.project.sofeiandrei.user.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.sofeiandrei.expense.model.Expense;
import com.project.sofeiandrei.trip.model.Trip;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId" )
public class User implements Serializable {
  @Getter
  public static User signedInUser;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  @Getter
  @Setter
  private Long userId;

  @Getter
  @Setter
  @Column(nullable = false)
  @NotBlank(message = "Username is mandatory")
  private String username;

  @Getter
  @Setter
  @Column(unique = true, nullable = false)
  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  @Getter
  @Setter
  @Column(nullable = false)
  @NotBlank(message = "Password is mandatory")
  private String password;

  @Getter
  @Setter
  @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
  private Set<Trip> trips = new HashSet<Trip>();

  @Getter
  @Setter
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "expense_participants", schema = "public",
          joinColumns = {@JoinColumn(name = "user_id")},
          inverseJoinColumns = {@JoinColumn(name = "expense_id")})
  private Set<Expense> expenses = new HashSet<Expense>();

  public User(Long userId, String username, String email, String password) {
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public User() {}

  @Override
  public String toString() {
    return "User{" +
      "userId=" + userId +
      ", username='" + username + '\'' +
      ", email='" + email + '\'' +
      ", password='" + password + '\'' +
      '}';
  }

  public void addTrip(Trip trip) {
    trips.add(trip);
    trip.getUsers().add(this);
  }

  public void removeTrip(Trip trip) {
    trips.remove(trip);
    trip.getUsers().remove(this);
  }
}
