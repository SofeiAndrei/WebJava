package com.project.sofeiandrei.expense_image.model;

import com.project.sofeiandrei.expense.model.Expense;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expense_images")
public class ExpenseImage {
  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long expenseImageId;

  @Getter
  @Setter
  @Column(nullable = false)
  private String name;

  @Getter
  @Setter
  @ManyToOne
  @JoinColumn(name = "expense_id")
  private Expense expense;

  @Lob
  @Getter
  @Setter
  private byte[] imageData;
}
