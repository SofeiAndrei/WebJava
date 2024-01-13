package com.project.sofeiandrei.user.repositories;

import com.project.sofeiandrei.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  User findByEmail(String username);
  User findByEmailAndPassword(String email, String password);
  Boolean existsByEmail(String email);
}
