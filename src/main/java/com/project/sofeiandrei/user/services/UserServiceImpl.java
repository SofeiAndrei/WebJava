package com.project.sofeiandrei.user.services;

import com.project.sofeiandrei.user.model.User;
import com.project.sofeiandrei.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Override
  public User createUser(User user) throws Exception {
    return userRepository.save(user);
  }

  @Override
  public User login(User user) throws Exception {
    Boolean userExists = userRepository.existsByEmail(user.getEmail());

    if (!userExists) {
      throw new Exception("User does not exist");
    } else {
      User storedUser = userRepository.findByEmail(user.getEmail());
      if (user.getPassword().equals(storedUser.getPassword())) {
        User.signedInUser = storedUser;
        return storedUser;
      } else {
        throw new Exception("Wrong password");
      }
    }


  }
}
