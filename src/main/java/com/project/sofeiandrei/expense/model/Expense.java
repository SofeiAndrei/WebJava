package com.project.sofeiandrei.expense.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.sofeiandrei.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "expenses")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "expenseId" )
public class Expense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  @Getter
  @Setter
  private Long expenseId;

  @Getter
  @Setter
  @Column(nullable = false)
  private String name;

  @Getter
  @Setter
  @Column(nullable = false)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  private Date incurredAt;

  @Getter
  @Setter
  @Column(nullable = false)
  private Float amount;

  @Getter
  @Setter
  @ManyToMany(mappedBy = "expenses", cascade = CascadeType.ALL)
  private Set<User> users = new HashSet<User>();

  public void addParticipant(User user) {
    users.add(user);
    user.getExpenses().add(this);
  }
  public void removeParticipant(User user) {
    users.remove(user);
    user.getExpenses().remove(this);
  }
}
