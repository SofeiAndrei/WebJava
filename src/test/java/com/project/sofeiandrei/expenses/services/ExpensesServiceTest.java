package com.project.sofeiandrei.expenses.services;

import com.project.sofeiandrei.expense.model.Expense;
import com.project.sofeiandrei.expense.repositories.ExpenseRepository;
import com.project.sofeiandrei.expense.services.ExpenseServiceImpl;
import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.repositories.TripRepository;
import com.project.sofeiandrei.trip_invitation.services.TripInvitationServiceImpl;
import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ExpensesServiceTest {
  @Mock
  ExpenseRepository expenseRepository;

  @Mock
  TripRepository tripRepository;

  @Mock
  UserRepository userRepository;

  @InjectMocks
  private ExpenseServiceImpl expenseService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateExpense_Success() {
    Long tripId = 1L;
    Long expenseId = 1L;

    User mockUser = new User();
    mockUser.setUserId(1L);

    User.signedInUser = mockUser;

    Trip mockTrip = new Trip();
    mockTrip.setTripId(tripId);

    Expense mockExpense = new Expense();
    mockExpense.setExpenseId(expenseId);

    try (MockedStatic<User> mockedUser = mockStatic(User.class)) {

      when(userRepository.findById(User.signedInUser.getUserId())).thenReturn(java.util.Optional.of(mockUser));

      mockedUser.when(User::getSignedInUser).thenReturn(mockUser);

      when(tripRepository.findById(tripId)).thenReturn(java.util.Optional.of(mockTrip));
      when(expenseRepository.save(mockExpense)).thenReturn(mockExpense);

      Expense result = expenseService.createExpense(mockExpense, tripId);

      assertEquals(mockExpense, result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testCreateExpense_FailureNotSignedIn() {
    User mockSender = new User();
    Long tripId = 1L;

    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(null);
      when(User.getSignedInUser()).thenReturn(null);
    };

    Expense mockExpense = new Expense();

    assertThrowsExactly(Exception.class, () -> {
      expenseService.createExpense(mockExpense, tripId);
    }, "User not signed in");
  }

  @Test
  void testCreateExpense_FailureTripNotFound() {
    Long tripId = 1L;

    User mockUser = new User();
    mockUser.setUserId(1L);

    User.signedInUser = mockUser;

    Expense mockExpense = new Expense();

    try (MockedStatic<User> mockedStatic = mockStatic(User.class)){
      mockedStatic.when(User::getSignedInUser).thenReturn(mockUser);
      when(User.getSignedInUser()).thenReturn(mockUser);
    };

    assertThrowsExactly(Exception.class, () -> {
      expenseService.createExpense(mockExpense, tripId);
    }, "Trip not found");
  }

  @Test
  void testFindById_Success() {
    Long expenseId = 1L;

    Expense mockExpense = new Expense();
    mockExpense.setExpenseId(expenseId);

    when(expenseRepository.findById(expenseId)).thenReturn(java.util.Optional.of(mockExpense));

    try {
      Expense result = expenseService.findById(expenseId);

      assertEquals(mockExpense, result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testFindById_Failure() {
    Long expenseId = 1L;

    when(expenseRepository.findById(expenseId)).thenReturn(java.util.Optional.empty());

    assertThrowsExactly(Exception.class, () -> {
      expenseService.findById(expenseId);
    }, "Expense not found");
  }

  @Test
  void testUpdateExpense_Success() {
    Long expenseId = 1L;

    Expense mockExpense = new Expense();
    mockExpense.setExpenseId(expenseId);

    when(expenseRepository.existsById(expenseId)).thenReturn(true);
    when(expenseRepository.save(mockExpense)).thenReturn(mockExpense);

    try {
      Expense result = expenseService.updateExpense(expenseId, mockExpense);

      assertEquals(mockExpense, result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testUpdateExpense_Failure() {
    Long expenseId = 1L;

    Expense mockExpense = new Expense();
    mockExpense.setExpenseId(expenseId);

    when(expenseRepository.existsById(expenseId)).thenReturn(false);

    assertThrowsExactly(Exception.class, () -> {
      expenseService.updateExpense(expenseId, mockExpense);
    }, "Expense not found");
  }

  @Test
  void testDeleteExpense_Success() {
    Long expenseId = 1L;

    when(expenseRepository.existsById(expenseId)).thenReturn(true);

    try {
      String result = expenseService.deleteExpense(expenseId);

      assertEquals("Expense deleted successfully", result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testDeleteExpense_Failure() {
    Long expenseId = 1L;

    when(expenseRepository.existsById(expenseId)).thenReturn(false);

    assertThrowsExactly(Exception.class, () -> {
      expenseService.deleteExpense(expenseId);
    }, "Expense not found");
  }

  @Test
  void testAddUserToExpense_Success() {
    Long expenseId = 1L;
    Long userId = 1L;

    Expense mockExpense = new Expense();
    mockExpense.setExpenseId(expenseId);

    User mockUser = new User();
    mockUser.setUserId(userId);

    when(expenseRepository.findById(expenseId)).thenReturn(java.util.Optional.of(mockExpense));
    when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(mockUser));
    when(expenseRepository.save(mockExpense)).thenReturn(mockExpense);

    try {
      Expense result = expenseService.addUserToExpense(expenseId, userId);

      assertEquals(mockExpense, result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
void testAddUserToExpense_FailureExpenseNotFound() {
    Long expenseId = 1L;
    Long userId = 1L;

    User mockUser = new User();
    mockUser.setUserId(userId);

    when(expenseRepository.findById(expenseId)).thenReturn(java.util.Optional.empty());

    assertThrowsExactly(Exception.class, () -> {
      expenseService.addUserToExpense(expenseId, userId);
    }, "Expense not found");
  }

  @Test
  void testAddUserToExpense_FailureUserNotFound() {
    Long expenseId = 1L;
    Long userId = 1L;

    Expense mockExpense = new Expense();
    mockExpense.setExpenseId(expenseId);

    when(expenseRepository.findById(expenseId)).thenReturn(java.util.Optional.of(mockExpense));
    when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

    assertThrowsExactly(Exception.class, () -> {
      expenseService.addUserToExpense(expenseId, userId);
    }, "User not found");
  }

  @Test
  void testFindAllExpensesByUserId_Success() {
    Long userId = 1L;

    Expense mockExpense = new Expense();

    when(tripRepository.existsById(userId)).thenReturn(true);
    when(expenseRepository.findAllExpensesByUserId(userId)).thenReturn(java.util.List.of(mockExpense));

    try {
      List<Expense> result = expenseService.findAllExpensesByUserId(userId);

      assertEquals(java.util.List.of(mockExpense), result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testFindAllExpensesByUserId_Failure() {
    Long userId = 1L;

    when(tripRepository.existsById(userId)).thenReturn(false);

    assertThrowsExactly(Exception.class, () -> {
      expenseService.findAllExpensesByUserId(userId);
    }, "User does not exist");
  }
}
