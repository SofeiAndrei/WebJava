package com.project.sofeiandrei.trip.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.sofeiandrei.expense.model.Expense;
import com.project.sofeiandrei.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "trips")
@NoArgsConstructor
@AllArgsConstructor
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
  @NotBlank(message = "Location is mandatory")
  private String location;

  @Getter
  @Setter
  @Column(nullable = false)
  @NotBlank(message = "Start Date is mandatory")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date startDate;

  @Getter
  @Setter
  @Column(nullable = false)
  @NotBlank(message = "End Date is mandatory")
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date endDate;

  @Getter
  @Setter
  @ManyToMany
  @JoinTable(name = "trip_participants", schema = "public",
          joinColumns = {@JoinColumn(name = "trip_id")},
          inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private Set<User> users = new HashSet<User>();

  @Getter
  @Setter
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "trip_id")
  private List<Expense> expenses = new ArrayList<Expense>();

  public void addExpense(Expense expense) {
    expenses.add(expense);
  }

  public void removeExpense(Expense expense) {
    expenses.remove(expense);
  }
}
