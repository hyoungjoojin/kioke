package com.kioke.user.service;

import com.kioke.user.dto.data.user.CreateUserDto;
import com.kioke.user.exception.UserDoesNotExistException;
import com.kioke.user.exception.UserDoesNotExistException.UserIdentifierType;
import com.kioke.user.model.User;
import com.kioke.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired @Lazy UserRepository userRepository;

  public void createUser(CreateUserDto data) {
    User user =
        User.builder()
            .uid(data.getUid())
            .email(data.getEmail())
            .firstName(data.getFirstName())
            .lastName(data.getLastName())
            .build();
    userRepository.save(user);
  }

  public User getUserById(String uid) throws UserDoesNotExistException {
    return userRepository
        .findById(uid)
        .orElseThrow(() -> new UserDoesNotExistException(UserIdentifierType.UID, uid));
  }
}
