package io.kioke.feature.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import autoparams.AutoParams;
import io.kioke.exception.user.UserAlreadyExistsException;
import io.kioke.exception.user.UserNotFoundException;
import io.kioke.feature.user.domain.User;
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

  @Test
  @AutoParams
  public void getUserById_ifUserDoesNotExist_thenThrowUserNotFoundException(String userId) {
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

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
  @AutoParams
  public void createUser_ifUserExists_thenThrowUserAlreadyExistsException(String email) {
    when(userRepository.findByEmail(email))
        .thenReturn(Optional.of(User.builder().email(email).build()));

    assertThrows(
        UserAlreadyExistsException.class,
        () -> {
          userService.createUser(email);
        });
  }
}
