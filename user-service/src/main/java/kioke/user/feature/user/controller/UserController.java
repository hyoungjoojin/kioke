package kioke.user.feature.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import kioke.commons.annotation.HttpResponse;
import kioke.user.feature.user.dto.data.UserDto;
import kioke.user.feature.user.dto.response.GetMyInformationResponseBodyDto;
import kioke.user.feature.user.exception.UserNotFoundException;
import kioke.user.feature.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User API", description = "API endpoints for user management")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users/me")
  @HttpResponse(status = HttpStatus.OK)
  @Operation(
      summary = "Get My Information",
      description = "Get information about the authenticated user.")
  public GetMyInformationResponseBodyDto getMyInformation(@AuthenticationPrincipal String userId)
      throws UserNotFoundException {
    Optional<UserDto> user = userService.getUserById(userId);
    if (user.isEmpty()) {
      throw new UserNotFoundException();
    }

    return GetMyInformationResponseBodyDto.from(user.get());
  }
}
