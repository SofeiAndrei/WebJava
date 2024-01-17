package com.project.sofeiandrei.expenses.controllers;

import com.project.sofeiandrei.expense.controllers.ExpenseController;
import com.project.sofeiandrei.expense.services.ExpenseService;
import com.project.sofeiandrei.expense.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.when;

public class ExpenseControllerTest {

  @Mock
  ExpenseService expenseService;

  @InjectMocks
  private ExpenseController expenseController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateExpense_Success() throws Exception {
    Expense mockExpense = new Expense();

    when(expenseService.createExpense(mockExpense, 1L)).thenReturn(mockExpense);

    ResponseEntity<Expense> responseEntity = expenseController.createExpense(mockExpense, 1L);

    assert(responseEntity.getBody() == mockExpense);
  }

  @Test
  void testCreateExpense_Failure() throws Exception {
    Expense mockExpense = new Expense();
    when(expenseService.createExpense(mockExpense, 1L)).thenThrow(new Exception("Trip not found"));

    assertThrowsExactly(Exception.class, () -> {
      expenseController.createExpense(mockExpense, 1L);
    }, "Trip not found");
  }

  @Test
  void testGetExpense_Success() throws Exception {
    Expense mockExpense = new Expense();
    when(expenseService.findById(1L)).thenReturn(mockExpense);

    ResponseEntity<Expense> responseEntity = expenseController.getExpense(1L);

    assert(responseEntity.getBody() == mockExpense);
  }

  @Test
  void testGetExpense_Failure() throws Exception {
    when(expenseService.findById(1L)).thenThrow(new Exception("Expense not found"));

    assertThrowsExactly(Exception.class, () -> {
      expenseController.getExpense(1L);
    }, "Expense not found");
  }

  @Test
  void testGetAllExpensesForUser_Success() throws Exception {
    Expense mockExpense = new Expense();
    when(expenseService.findAllExpensesByUserId(1L)).thenReturn(java.util.List.of(mockExpense));

    ResponseEntity<java.util.List<Expense>> responseEntity = expenseController.getAllExpensesForUser(1L);

    assert(responseEntity.getBody().get(0) == mockExpense);
  }

  @Test
  void testGetAllExpensesForUser_Failure() throws Exception {
    when(expenseService.findAllExpensesByUserId(1L)).thenThrow(new Exception("User not found"));

    assertThrowsExactly(Exception.class, () -> {
      expenseController.getAllExpensesForUser(1L);
    }, "User not found");
  }

  @Test
  void testUpdateExpense_Success() throws Exception {
    Expense mockExpense = new Expense();
    when(expenseService.updateExpense(1L, mockExpense)).thenReturn(mockExpense);

    ResponseEntity<Expense> responseEntity = expenseController.updateExpense(1L, mockExpense);

    assert(responseEntity.getBody() == mockExpense);
  }

  @Test
  void testUpdateExpense_Failure() throws Exception {
    Expense mockExpense = new Expense();
    when(expenseService.updateExpense(1L, mockExpense)).thenThrow(new Exception("Expense not found"));

    assertThrowsExactly(Exception.class, () -> {
      expenseController.updateExpense(1L, mockExpense);
    }, "Expense not found");
  }

  @Test
  void testAddUserToExpense_Success() throws Exception {
    Expense mockExpense = new Expense();
    when(expenseService.addUserToExpense(1L, 1L)).thenReturn(mockExpense);

    ResponseEntity<Expense> responseEntity = expenseController.addUserToExpense(1L, 1L);

    assert(responseEntity.getBody() == mockExpense);
  }

  @Test
  void testAddUserToExpense_Failure() throws Exception {
    when(expenseService.addUserToExpense(1L, 1L)).thenThrow(new Exception("Expense not found"));

    assertThrowsExactly(Exception.class, () -> {
      expenseController.addUserToExpense(1L, 1L);
    }, "Expense not found");
  }

  @Test
  void testDeleteExpense_Success() throws Exception {
    when(expenseService.deleteExpense(1L)).thenReturn("Expense deleted successfully");

    ResponseEntity<String> responseEntity = expenseController.deleteExpense(1L);

    assert(responseEntity.getBody() == "Expense deleted successfully");
  }

  @Test
  void testDeleteExpense_Failure() throws Exception {
    when(expenseService.deleteExpense(1L)).thenThrow(new Exception("Expense not found"));

    assertThrowsExactly(Exception.class, () -> {
      expenseController.deleteExpense(1L);
    }, "Expense not found");
  }
}
