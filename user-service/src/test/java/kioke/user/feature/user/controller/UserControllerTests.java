package kioke.user.feature.user.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;
import kioke.user.common.auth.AuthService;
import kioke.user.config.AspectConfig;
import kioke.user.feature.user.domain.User;
import kioke.user.feature.user.dto.data.UserDto;
import kioke.user.feature.user.service.UserService;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({AspectConfig.class})
public class UserControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;
  @MockBean private AuthService authService;

  private Faker faker = new Faker();

  private User user =
      new User(faker.internet().uuidv4(), faker.internet().emailAddress(), faker.name().fullName());

  @BeforeEach
  private void setUp() {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(user.getUserId(), null, Collections.emptyList());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }

  @AfterEach
  private void cleanUp() {
    SecurityContextHolder.clearContext();
  }

  @Test
  public void test_getMyInformation_givenUserId_whenUserIdExists_thenReturnSuccess()
      throws Exception {
    given(userService.getUserById(user.getUserId())).willReturn(Optional.of(UserDto.from(user)));

    mockMvc.perform(get("/users/me")).andExpect(status().isOk());
  }

  @Test
  public void test_getMyInformation_givenUserId_whenUserIdDoesNotExist_thenReturnNotFound()
      throws Exception {
    given(userService.getUserById(user.getUserId())).willReturn(Optional.empty());

    mockMvc.perform(get("/users/me")).andExpect(status().isNotFound());
  }

  @Test
  public void test_getMyInformation_givenUserId_whenUserIdNull_thenReturnInternalServerError()
      throws Exception {
    SecurityContextHolder.clearContext();

    given(userService.getUserById(null)).willThrow(NullPointerException.class);

    mockMvc.perform(get("/users/me")).andExpect(status().isInternalServerError());
  }
}
