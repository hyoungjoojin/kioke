package com.kioke.auth.service;

import com.kioke.auth.exception.UserDoesNotExistException;
import com.kioke.auth.model.User;
import com.kioke.auth.repository.UserRepository;
import java.util.NoSuchElementException;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  @Autowired @Lazy UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
    return userRepository
        .findById(uid)
        .orElseThrow(
            () -> {
              throw new UsernameNotFoundException(uid);
            });
  }

  public User getUserByEmail(String email) throws UserDoesNotExistException {
    return userRepository
        .findUserByEmail(email)
        .orElseThrow(() -> new UserDoesNotExistException(email));
  }

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
