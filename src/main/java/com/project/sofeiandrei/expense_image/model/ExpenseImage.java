package com.project.sofeiandrei.expense_image.model;

import com.project.sofeiandrei.expense.model.Expense;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
  @NotBlank(message = "Name is mandatory")
  private String name;

  @Setter
  @ManyToOne
  @JoinColumn(name = "expense_id")
  @NotBlank(message = "Expense is mandatory")
  private Expense expense;

  @Lob
  @Setter
  private byte[] imageData;
}
