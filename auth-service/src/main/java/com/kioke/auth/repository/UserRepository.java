package com.kioke.auth.repository;

import com.kioke.auth.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  public Optional<User> findUserByEmail(String email);
}
