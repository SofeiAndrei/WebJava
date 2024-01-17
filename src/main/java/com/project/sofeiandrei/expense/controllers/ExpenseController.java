package com.project.sofeiandrei.expense.controllers;

import com.project.sofeiandrei.expense.model.Expense;
import com.project.sofeiandrei.expense.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("expense_controller")
@RequestMapping("/api/expenses")
public class ExpenseController {

  @Autowired
  ExpenseService expenseService;

  @PostMapping("")
  public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, @RequestParam Long tripId) throws Exception {
    return ResponseEntity.ok(expenseService.createExpense(expense, tripId));
  }

  @GetMapping("/{expenseId}")
  public ResponseEntity<Expense> getExpense(@PathVariable("expenseId") Long expenseId) throws Exception {
    return ResponseEntity.ok(expenseService.findById(expenseId));
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Expense>> getAllExpensesForUser(@PathVariable("userId") Long userId) throws Exception {
    return ResponseEntity.ok(expenseService.findAllExpensesByUserId(userId));
  }

  @PutMapping("/{expenseId}")
  public ResponseEntity<Expense> updateExpense(@PathVariable("expenseId") Long expenseId, @RequestBody Expense expense) throws Exception {
    return ResponseEntity.ok(expenseService.updateExpense(expenseId, expense));
  }

  @PutMapping("/{expenseId}/add_user")
  public ResponseEntity<Expense> addUserToExpense(@PathVariable("expenseId") Long expenseId, @RequestParam Long userId) throws Exception {
    return ResponseEntity.ok(expenseService.addUserToExpense(expenseId, userId));
  }

  @DeleteMapping("/{expenseId}")
  public ResponseEntity<String> deleteExpense(@PathVariable("expenseId") Long expenseId) throws Exception {
    return ResponseEntity.ok(expenseService.deleteExpense(expenseId));
  }
}
