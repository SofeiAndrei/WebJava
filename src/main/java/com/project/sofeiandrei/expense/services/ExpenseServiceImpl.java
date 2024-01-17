package com.project.sofeiandrei.expense.services;

import com.project.sofeiandrei.expense.model.Expense;
import com.project.sofeiandrei.expense.repositories.ExpenseRepository;
import com.project.sofeiandrei.trip.model.Trip;
import com.project.sofeiandrei.trip.repositories.TripRepository;
import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("expense_service")
@Transactional
public class ExpenseServiceImpl implements ExpenseService{
  @Autowired
  ExpenseRepository expenseRepository;

  @Autowired
  TripRepository tripRepository;

  @Autowired
  UserRepository userRepository;

  @Override
  public Expense createExpense(Expense expense, Long tripId) throws Exception {
    if (User.getSignedInUser() == null) {
      throw new Exception("User not signed in");
    }

    Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new Exception("Trip not found"));

    trip.addExpense(expense);

    expense.addParticipant(User.getSignedInUser());

    return expenseRepository.save(expense);
  }

  @Override
  public Expense findById(Long expenseId) throws Exception {
    return expenseRepository.findById(expenseId).orElseThrow(() -> new Exception("Expense not found"));
  }

  @Override
  public Expense updateExpense(Long expenseId, Expense expense) throws Exception {
    Boolean expenseExists = expenseRepository.existsById(expenseId);

    if (!expenseExists) {
      throw new Exception("Expense not found");
    } else {
      return expenseRepository.save(expense);
    }
  }

  @Override
  public String deleteExpense(Long expenseId) throws Exception {
    Boolean expenseExists = expenseRepository.existsById(expenseId);

    if (!expenseExists) {
      throw new Exception("Expense not found");
    } else {
      expenseRepository.deleteById(expenseId);
      return "Expense deleted successfully";
    }
  }

  @Override
  public List<Expense> findAllExpensesByUserId(Long userId) throws Exception {
    Boolean userExists = tripRepository.existsById(userId);

    if (!userExists) {
      throw new Exception("User does not exist");
    } else {
      return expenseRepository.findAllExpensesByUserId(userId);
    }
  }

  @Override
  public Expense addUserToExpense(Long expenseId, Long userId) throws Exception {
    Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new Exception("Expense not found"));
    User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));

    expense.addParticipant(user);

    return expenseRepository.save(expense);
  }
}
