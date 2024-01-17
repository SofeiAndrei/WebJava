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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
