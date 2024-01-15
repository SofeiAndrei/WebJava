package com.project.sofeiandrei.expense_image.services;

import com.project.sofeiandrei.expense.model.Expense;
import com.project.sofeiandrei.expense.repositories.ExpenseRepository;
import com.project.sofeiandrei.expense_image.model.ExpenseImage;
import com.project.sofeiandrei.expense_image.repositories.ExpenseImageRepository;
import com.project.sofeiandrei.expense_image.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service("expense_image_service")
@Transactional
public class ExpenseImageServiceImpl implements ExpenseImageService {
  @Autowired
  ExpenseImageRepository expenseImageRepository;

  @Autowired
  ExpenseRepository expenseRepository;

  @Override
  public String uploadImage(MultipartFile imageFile, Long expenseId) throws Exception {
    Expense expense = expenseRepository.findById(expenseId).orElseThrow(() -> new Exception("Expense not found"));

    ExpenseImage imageToSave = new ExpenseImage();
    imageToSave.setName(StringUtils.cleanPath(imageFile.getOriginalFilename()));
    imageToSave.setExpense(expense);
    imageToSave.setImageData(imageFile.getBytes());

    expenseImageRepository.save(imageToSave);
    return "file uploaded successfully : " + imageFile.getOriginalFilename();
  }

  @Override
  public ExpenseImage downloadImage(Long expenseImageId) throws Exception {
    return expenseImageRepository.findById(expenseImageId).orElseThrow(() -> new Exception("Image not found"));
  }

  @Override
  public List<ExpenseImage> getAllImagesForExpense(Long expenseId) throws IOException {
    return expenseImageRepository.findAllByExpenseExpenseId(expenseId);
  }
}
