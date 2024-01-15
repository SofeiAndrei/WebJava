package com.project.sofeiandrei.expense_image.model;

import com.project.sofeiandrei.expense.model.Expense;
import lombok.Getter;
import lombok.Setter;

public class ExpenseImageResponse {
  @Getter
  @Setter
  private Long expenseImageId;

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String url;

  @Getter
  @Setter
  private Expense expense;
}
