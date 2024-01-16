package com.project.sofeiandrei.expense_image.model;

import com.project.sofeiandrei.expense.model.Expense;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expense_images")
public class ExpenseImage {
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long expenseImageId;

  @Setter
  @Column(nullable = false)
  private String name;

  @Setter
  @ManyToOne
  @JoinColumn(name = "expense_id")
  private Expense expense;

  @Lob
  @Setter
  private byte[] imageData;
}
