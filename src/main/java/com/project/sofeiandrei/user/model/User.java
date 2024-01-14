package com.project.sofeiandrei.user.model;

import com.project.sofeiandrei.trip.model.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
  @Getter
  public static User signedInUser;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  @Getter
  @Setter
  private Long userId;

  @Getter
  @Setter
  private String username;

  @Getter
  @Setter
  @Column(unique = true)
  private String email;

  @Getter
  @Setter
  private String password;

  @Getter
  @Setter
  @ManyToMany(mappedBy = "users")
  private Set<Trip> trips = new HashSet<Trip>();

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
}
