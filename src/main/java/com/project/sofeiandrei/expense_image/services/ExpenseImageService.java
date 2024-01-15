package com.project.sofeiandrei.expense_image.services;

import com.project.sofeiandrei.expense_image.model.ExpenseImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExpenseImageService {
  public String uploadImage(MultipartFile imageFile, Long expenseId) throws Exception;
  public ExpenseImage downloadImage(Long expenseImageId) throws Exception;
  public List<ExpenseImage> getAllImagesForExpense(Long expenseId) throws IOException;
}
