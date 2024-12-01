package com.kioke.auth.service;

import com.kioke.auth.model.User;
import com.kioke.auth.repository.UserRepository;
import java.util.NoSuchElementException;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired @Lazy UserRepository userRepository;

  public User getUser(String uid) throws NoSuchElementException {
    return userRepository.findById(uid).get();
  }

  public User getOrCreateUser(String uid) {
    try {
      return userRepository.findById(uid).get();
    } catch (NoSuchElementException e) {
      return userRepository.save(User.builder().uid(uid).build());
    }
  }
}
