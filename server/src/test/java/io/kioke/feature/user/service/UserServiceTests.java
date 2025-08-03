package io.kioke.feature.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import io.kioke.exception.user.UserAlreadyExistsException;
import io.kioke.exception.user.UserNotFoundException;
import io.kioke.feature.user.dto.UserDto;
import io.kioke.feature.user.mapper.UserMapper;
import io.kioke.feature.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

  @InjectMocks private UserService userService;

  @Mock private UserRepository userRepository;
  @Mock private UserMapper userMapper;

  @Test
  public void getUserById_ifUserDoesNotExist_thenThrowUserNotFoundException() {
    String userId = "userId";

    when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

    assertThrows(
        UserNotFoundException.class,
        () -> {
          userService.getUserById(userId);
        });
  }

  @Test
  public void createUser_ifEmailIsNull_thenThrowInvalidArgument() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          userService.createUser(null);
        });
  }

  @Test
  public void createUser_ifUserExists_thenThrowUserAlreadyExistsException() {
    String email = "email";
    when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(new UserDto("userId")));

    assertThrows(
        UserAlreadyExistsException.class,
        () -> {
          userService.createUser(email);
        });
  }
}
