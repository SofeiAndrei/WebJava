package com.project.sofeiandrei.expense_images.services;

import com.project.sofeiandrei.expense.repositories.ExpenseRepository;
import com.project.sofeiandrei.expense.model.Expense;
import com.project.sofeiandrei.expense_image.model.ExpenseImage;
import com.project.sofeiandrei.expense_image.repositories.ExpenseImageRepository;
import com.project.sofeiandrei.expense_image.services.ExpenseImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.Mockito.*;

public class ExpenseImageServiceTest {
  @Mock
  ExpenseImageRepository expenseImageRepository;

  @Mock
  ExpenseRepository expenseRepository;

  @InjectMocks
  ExpenseImageServiceImpl expenseImageService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testUploadImage_Success() throws Exception {
    Long expenseId = 1L;
    MultipartFile mockFile = new MockMultipartFile("test-image.png", "test-image.png", String.valueOf(MediaType.IMAGE_PNG), new byte[]{});

    Expense mockExpense = new Expense();
    mockExpense.setExpenseId(expenseId);

    when(expenseRepository.findById(expenseId)).thenReturn(Optional.of(mockExpense));

    ExpenseImage mockExpenseImage = new ExpenseImage();
    mockExpenseImage.setExpense(mockExpense);
    mockExpenseImage.setName("test-image.png");
    mockExpenseImage.setImageData(new byte[]{});

    when(expenseImageRepository.save(mockExpenseImage)).thenReturn(mockExpenseImage);

    String result = expenseImageService.uploadImage(mockFile, expenseId);

    assertEquals("file uploaded successfully : test-image.png", result);
    verify(expenseImageRepository, times(1)).save(mockExpenseImage);
  }

  @Test
  void testUploadImage_ExpenseNotFound() {
    Long expenseId = 1L;
    MultipartFile mockFile = new MockMultipartFile("test-image.png", new byte[]{});

    when(expenseRepository.findById(expenseId)).thenReturn(Optional.empty());

    assertThrows(Exception.class, () -> expenseImageService.uploadImage(mockFile, expenseId));
  }

  @Test
  void testDownloadImage_Success() throws Exception {
    Long expenseImageId = 1L;

    byte[] imageData = new byte[]{1, 2, 3};

    ExpenseImage mockExpenseImage = new ExpenseImage();
    mockExpenseImage.setImageData(imageData);

    when(expenseImageRepository.findById(expenseImageId)).thenReturn(Optional.of(mockExpenseImage));

    byte[] result = expenseImageService.downloadImage(expenseImageId);

    assertArrayEquals(imageData, result);
  }

  @Test
  void testDownloadImage_ImageNotFound() {
    Long expenseImageId = 1L;

    when(expenseImageRepository.findById(expenseImageId)).thenReturn(Optional.empty());

    assertThrows(Exception.class, () -> expenseImageService.downloadImage(expenseImageId));
  }

  @Test
  void testGetAllImagesForExpense_Success() throws Exception {
    Long expenseId = 1L;

    ExpenseImage mockExpenseImage = new ExpenseImage();
    mockExpenseImage.setExpense(new Expense());
    mockExpenseImage.setName("test-image.png");
    mockExpenseImage.setExpenseImageId(1L);
    mockExpenseImage.setImageData(new byte[]{1, 2, 3});

    when(expenseImageRepository.findAllByExpenseExpenseId(expenseId)).thenReturn(java.util.List.of(mockExpenseImage));

    var result = expenseImageService.getAllImagesForExpense(expenseId);

    assertEquals(1, result.size());
    assertEquals("test-image.png", result.get(0).getName());
    assertEquals(1L, result.get(0).getExpenseImageId());
    assertEquals(mockExpenseImage.getExpense(), result.get(0).getExpense());
  }

  @Test
  void testGetAllImagesForExpense_ExpenseNotFound() throws IOException {
    Long expenseId = 1L;

    when(expenseRepository.findById(expenseId)).thenReturn(null);

    when(expenseImageRepository.findAllByExpenseExpenseId(expenseId)).thenReturn(java.util.List.of());

    List<ExpenseImage> result = expenseImageService.getAllImagesForExpense(expenseId);

    assertEquals(0, result.size());
  }
}
