package io.kioke.feature.user.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.kioke.config.JpaTestConfig;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaTestConfig.class)
public class UserRepositoryTests {

  @Autowired private UserRepository userRepository;

  @Test
  public void findUserById_ifUserIdIsNull_thenReturnEmpty() {
    assertEquals(Optional.empty(), userRepository.findUserById(null));
  }

  @Test
  public void findUserById_ifUserIdDoesNotExist_thenReturnEmpty() {
    assertEquals(Optional.empty(), userRepository.findUserById("userId"));
  }

  @Test
  public void findUserById_ifUserIdDoesExist_thenReturnUser() {
    User user = new User();
    user.setEmail("email");
    user = userRepository.save(user);

    assertEquals(
        Optional.of(new UserDto(user.getUserId())), userRepository.findUserById(user.getUserId()));
  }

  @Test
  public void findUserByEmail_ifEmailIsNull_thenReturnEmpty() {
    assertEquals(Optional.empty(), userRepository.findUserByEmail(null));
  }

  @Test
  public void findUserByEmail_ifEmailDoesNotExist_thenReturnEmpty() {
    assertEquals(Optional.empty(), userRepository.findUserByEmail("email"));
  }

  @Test
  public void findUserByEmail_ifEmailDoesExist_thenReturnUser() {
    User user = new User();
    user.setEmail("email");
    user = userRepository.save(user);

    assertEquals(
        Optional.of(new UserDto(user.getUserId())),
        userRepository.findUserByEmail(user.getEmail()));
  }
}
