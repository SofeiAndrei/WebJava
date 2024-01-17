package com.project.sofeiandrei.expense.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.sofeiandrei.expense_image.model.ExpenseImage;
import com.project.sofeiandrei.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

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
  @NotBlank(message = "Name is mandatory")
  private String name;

  @Getter
  @Setter
  @Column(nullable = false)
  @DateTimeFormat(pattern = "dd/MM/yyyy")
  @NotBlank(message = "Incurred at Date is mandatory")
  private Date incurredAt;

  @Getter
  @Setter
  @Column(nullable = false)
  @NotBlank(message = "Amount is mandatory")
  private Float amount;

  @Getter
  @Setter
  @ManyToMany(mappedBy = "expenses", cascade = CascadeType.ALL)
  private Set<User> users = new HashSet<User>();

  @Getter
  @Setter
  @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ExpenseImage> expenseImages = new ArrayList<ExpenseImage>();

  public void addParticipant(User user) {
    users.add(user);
    user.getExpenses().add(this);
  }
  public void removeParticipant(User user) {
    users.remove(user);
    user.getExpenses().remove(this);
  }

  public void addExpenseImage(ExpenseImage expenseImage) {
    expenseImages.add(expenseImage);
  }

  public void removeExpenseImage(ExpenseImage expenseImage) {
    expenseImages.remove(expenseImage);
  }
}
