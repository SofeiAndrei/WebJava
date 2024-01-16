package com.project.sofeiandrei.expense_image.controllers;

import com.project.sofeiandrei.expense_image.model.ExpenseImage;
import com.project.sofeiandrei.expense_image.model.ExpenseImageResponse;
import com.project.sofeiandrei.expense_image.services.ExpenseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController("expense_image_controller")
@RequestMapping("/api/expenses/{expenseId}/expense_images")
public class ExpenseImageController {
  @Autowired
  ExpenseImageService expenseImageService;
  @PostMapping("")
  public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable("expenseId") Long expenseId) throws Exception {
    String uploadImage = expenseImageService.uploadImage(image, expenseId);

    return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
  }

  @GetMapping("/{expenseImageId}")
  public ResponseEntity<byte[]> downloadImage(@PathVariable("expenseImageId") Long expenseImageId) throws Exception {
    byte[] expenseImageData = expenseImageService.downloadImage(expenseImageId);
    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + expenseImageId + "\"")
            .body(expenseImageData);
  }

  @GetMapping("")
  public List<ExpenseImageResponse> downloadAllImagesForExpense(@PathVariable("expenseId") Long expenseId) throws Exception {
    return expenseImageService.getAllImagesForExpense(expenseId)
            .stream()
            .map(this::mapToExpenseImageResponse)
            .collect(Collectors.toList());
  }

  private ExpenseImageResponse mapToExpenseImageResponse(ExpenseImage expenseImage) {
    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/expenses/1/expense_images/")
            .path(expenseImage.getExpenseImageId().toString())
            .toUriString();
    ExpenseImageResponse expenseImageResponse = new ExpenseImageResponse();
    expenseImageResponse.setExpenseImageId(expenseImage.getExpenseImageId());
    expenseImageResponse.setName(expenseImage.getName());
    expenseImageResponse.setUrl(downloadURL);
    expenseImageResponse.setExpense(expenseImage.getExpense());

    return expenseImageResponse;
  }
}
