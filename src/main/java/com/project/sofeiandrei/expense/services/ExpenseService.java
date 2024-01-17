package com.project.sofeiandrei.expense.services;

import com.project.sofeiandrei.expense.model.Expense;

import java.util.List;

public interface ExpenseService {
  Expense createExpense(Expense expense, Long tripId) throws Exception;
  Expense findById(Long expenseId) throws Exception;
  Expense updateExpense(Long expenseId, Expense expense) throws Exception;
  String deleteExpense(Long expenseId) throws Exception;
  List<Expense> findAllExpensesByUserId(Long userId) throws Exception;
  Expense addUserToExpense(Long expenseId, Long userId) throws Exception;
}
