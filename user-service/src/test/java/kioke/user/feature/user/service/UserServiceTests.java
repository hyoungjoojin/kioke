package kioke.user.feature.user.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;

import kioke.user.feature.user.repository.UserRepository;
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
  public void test_getUserById_givenUserId_whenUserIdIsNull_thenThrowNullPointerException()
      throws Exception {
    assertThrows(
        NullPointerException.class,
        () -> {
          userService.getUserById(null);
        });

    verifyNoInteractions(userRepository);
  }
}
