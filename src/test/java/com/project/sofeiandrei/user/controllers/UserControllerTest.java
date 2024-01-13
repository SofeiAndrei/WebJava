package com.project.sofeiandrei.user.controllers;

import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import com.project.sofeiandrei.user.services.UserService;
import com.project.sofeiandrei.user.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
  @Mock
  private UserService userService;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSignUpSuccess() throws Exception {
    User.signedInUser = new User();
    ResponseEntity<User> responseEntity = userController.signUp(new User());
    assertEquals(200, responseEntity.getStatusCode().value());
  }

  @Test
  void testLoginSuccess() throws Exception {
    User.signedInUser = new User();
    ResponseEntity<User> responseEntity = userController.login(new User());
    assertEquals(200, responseEntity.getStatusCode().value());
    verify(userService, times(1)).login(any());
  }

  @Test
  void testLoginFailure() throws Exception {
    when(userController.login(any())).thenThrow(new Exception("User does not exist"));

    assertThrowsExactly(Exception.class, () -> {
      userController.login(new User());
    }, "User does not exist");
  }
}
