package com.project.sofeiandrei.expense.repositories;

import com.project.sofeiandrei.expense.model.Expense;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("expense_repository")
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
  @Query("SELECT e FROM Expense e JOIN e.users u WHERE u.id = :userId")
  List<Expense> findAllExpensesByUserId(Long userId);
}
