package com.project.sofeiandrei.user.services;

import com.project.sofeiandrei.user.model.User;

public interface UserService {
  User createUser(User user) throws Exception;

  User login(User user) throws Exception;
}
