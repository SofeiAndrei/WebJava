package com.project.sofeiandrei.expense_images.controllers;

import com.project.sofeiandrei.expense_image.controllers.ExpenseImageController;
import com.project.sofeiandrei.expense_image.services.ExpenseImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ExpenseImageControllerTest {
  @Mock
  ExpenseImageService expenseImageService;

  @InjectMocks
  ExpenseImageController expenseImageController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void uploadImage_Success() throws Exception {
    Long expenseId = 1L;
    MultipartFile mockFile = new MockMultipartFile("test-image.png", new byte[]{});
    when(expenseImageService.uploadImage(mockFile, expenseId)).thenReturn("file uploaded successfully : test-image.png");

    ResponseEntity<?> responseEntity = expenseImageController.uploadImage(mockFile, expenseId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals("file uploaded successfully : test-image.png", responseEntity.getBody());
  }

  @Test
  void uploadImage_ExpenseNotFound() throws Exception {
    Long expenseId = 1L;
    MultipartFile mockFile = new MockMultipartFile("test-image.png", new byte[]{});
    when(expenseImageService.uploadImage(mockFile, expenseId)).thenThrow(new Exception("Expense not found"));

    assertThrows(Exception.class, () -> expenseImageController.uploadImage(mockFile, expenseId));
    verify(expenseImageService, times(1)).uploadImage(mockFile, expenseId);
  }

  @Test
  void downloadImage_Success() throws Exception {
    Long expenseImageId = 1L;
    byte[] imageData = new byte[]{1, 2, 3};
    when(expenseImageService.downloadImage(expenseImageId)).thenReturn(imageData);

    ResponseEntity<byte[]> responseEntity = expenseImageController.downloadImage(expenseImageId);

    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    assertEquals(MediaType.IMAGE_PNG, responseEntity.getHeaders().getContentType());
    assertEquals("attachment; filename=\"" + expenseImageId + "\"", responseEntity.getHeaders().getFirst("Content-Disposition"));
    assertEquals(imageData, responseEntity.getBody());
  }

  @Test
  void downloadImage_ImageNotFound() throws Exception {
    Long expenseImageId = 1L;
    when(expenseImageService.downloadImage(expenseImageId)).thenThrow(new Exception("Image not found"));

    assertThrows(Exception.class, () -> expenseImageController.downloadImage(expenseImageId));
    verify(expenseImageService, times(1)).downloadImage(expenseImageId);
  }
}
