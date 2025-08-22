package io.kioke.feature.user.service;

import io.kioke.exception.user.UserAlreadyExistsException;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import io.kioke.feature.user.repository.UserRepository;
import io.kioke.feature.user.util.UserMapper;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Transactional
  public UserDto createUser(String email) throws UserAlreadyExistsException {
    Assert.notNull(email, "Email must not be null");

    if (userRepository.findByEmail(email).isPresent()) {
      throw new UserAlreadyExistsException();
    }

    User user = User.builder().email(email).build();
    user = userRepository.save(user);
    return userMapper.toDto(user);
  }

  @Transactional(readOnly = true)
  public Optional<User> findById(String userId) {
    return userRepository.findById(userId);
  }
}
