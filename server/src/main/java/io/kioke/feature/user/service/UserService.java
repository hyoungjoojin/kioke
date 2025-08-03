package io.kioke.feature.user.service;

import io.kioke.exception.user.UserAlreadyExistsException;
import io.kioke.exception.user.UserNotFoundException;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import io.kioke.feature.user.mapper.UserMapper;
import io.kioke.feature.user.repository.UserRepository;
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

  @Transactional(readOnly = true)
  public UserDto getUserById(String userId) throws UserNotFoundException {
    return userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException());
  }

  @Transactional(readOnly = true)
  public Optional<UserDto> findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  @Transactional
  public UserDto createUser(String email) throws UserAlreadyExistsException {
    Assert.hasLength(email, "Email must not be empty");

    if (findUserByEmail(email).isPresent()) {
      throw new UserAlreadyExistsException();
    }

    User user = new User();
    user.setEmail(email);

    user = userRepository.save(user);
    return userMapper.toUserDto(user);
  }
}
