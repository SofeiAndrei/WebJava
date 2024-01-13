package com.project.sofeiandrei.user.services;

import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSignUpUserSuccessful() throws Exception {
    User user = new User();
    user.setEmail("email@test.com");
    user.setUsername("username");
    user.setPassword("password");

    when(userRepository.existsByEmail("email@test.com")).thenReturn(false);
    when(userRepository.save(user)).thenReturn(user);

    User resultedUser = userService.signUpUser(user);

    assertNotNull(resultedUser);

    verify(userRepository, times(1)).existsByEmail("email@test.com");
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void testSignUpUserFailure() {
    User user = new User();
    user.setEmail("email@test.com");
    user.setUsername("username");
    user.setPassword("password");

    when(userRepository.existsByEmail("email@test.com")).thenReturn(true);

    assertThrowsExactly(Exception.class, () -> {
      userService.signUpUser(user);
    }, "Email is taken!");

    verify(userRepository, times(1)).existsByEmail("email@test.com");
    verify(userRepository, never()).save(user);
  }

  @Test
  void testLoginSuccessful() throws Exception {
    User user = new User();
    user.setEmail("email@test.com");
    user.setPassword("password");
    when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
    when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

    User loggedInUser = userService.login(user);
    assertEquals(user, loggedInUser);
    assertEquals(user, User.signedInUser);
  }

  @Test
  void testLoginFailure_UserDoesNotExist() {
    User user = new User();
    user.setEmail("notexistigemail@test.com");
    user.setPassword("password");
    when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

    assertThrowsExactly(Exception.class, () -> {
      userService.login(user);
    }, "User does not exist");
  }

  @Test
  void testLoginFailure_WrongPassword() {
    User user = new User();
    user.setEmail("email@test.com");
    user.setPassword("password");

    User secondUser = new User();
    secondUser.setEmail("email@test.com");
    secondUser.setPassword("another_password");
    when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
    when(userRepository.findByEmail(user.getEmail())).thenReturn(secondUser);

    assertThrowsExactly(Exception.class, () -> {
      userService.login(user);
    }, "Wrong password");
  }
}
