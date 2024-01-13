package com.project.sofeiandrei.user.controllers;

import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<User> signUp(@RequestBody User userData) throws Exception {
    return ResponseEntity.ok(userService.createUser(userData));
  }

  @PostMapping("/login")
  public ResponseEntity<User> login(@RequestBody User userData) throws Exception {
    return ResponseEntity.ok(userService.login(userData));
  }
}
