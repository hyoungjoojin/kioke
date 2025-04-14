package kioke.user.controller;

import java.util.Optional;
import kioke.commons.annotation.HttpResponse;
import kioke.user.dto.request.user.SearchUserRequestBodyDto;
import kioke.user.dto.response.user.GetMyInformationResponseBodyDto;
import kioke.user.dto.response.user.GetUserResponseBodyDto;
import kioke.user.dto.response.user.SearchUserResponseBodyDto;
import kioke.user.model.User;
import kioke.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  @HttpResponse(status = HttpStatus.OK)
  public GetMyInformationResponseBodyDto getMyInformation(@AuthenticationPrincipal String userId)
      throws UsernameNotFoundException {
    User user = userService.getUserById(userId);

    return GetMyInformationResponseBodyDto.from(user);
  }

  @GetMapping("/{userId}")
  @HttpResponse(status = HttpStatus.OK)
  public GetUserResponseBodyDto getUserById(@PathVariable(name = "userId") String userId)
      throws UsernameNotFoundException {
    User user = userService.getUserById(userId);

    return GetUserResponseBodyDto.from(user);
  }

  @PostMapping("/search")
  @HttpResponse(status = HttpStatus.OK)
  public SearchUserResponseBodyDto searchUser(
      @RequestBody SearchUserRequestBodyDto requestBodyDto) {
    Optional<User> user = userService.searchUser(requestBodyDto.email());

    return SearchUserResponseBodyDto.from(user);
  }
}
