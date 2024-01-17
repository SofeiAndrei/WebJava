package com.project.sofeiandrei.expense_image.repositories;

import com.project.sofeiandrei.expense_image.model.ExpenseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("expense_image_repository")
public interface ExpenseImageRepository extends JpaRepository<ExpenseImage, Long> {
  List<ExpenseImage> findAllByExpenseExpenseId(Long expenseId);
}
