package com.kioke.journal.service;

import com.kioke.journal.exception.user.UserNotFoundException;
import com.kioke.journal.model.User;
import com.kioke.journal.repository.UserRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired @Lazy private UserRepository userRepository;

  public User createUser(String uid) {
    Optional<User> user = userRepository.findById(uid);
    if (user.isPresent()) {
      return user.get();
    }

    User newUser = User.builder().uid(uid).journals(new ArrayList<>()).build();
    newUser = userRepository.save(newUser);
    return newUser;
  }

  public User getUserById(String uid) throws UserNotFoundException {
    return userRepository.findById(uid).orElseThrow(() -> new UserNotFoundException(uid));
  }
}
