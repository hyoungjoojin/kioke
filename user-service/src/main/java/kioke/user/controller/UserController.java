package kioke.user.controller;

import kioke.user.dto.response.data.user.GetUserResponseBodyDto;
import kioke.user.dto.response.data.user.GetUserResponseBodyDto.*;
import kioke.user.exception.user.UserDoesNotExistException;
import kioke.user.model.User;
import kioke.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired @Lazy UserService userService;

  @GetMapping
  public ResponseEntity<GetAuthenticatedUserResponseBodyDto> getAuthenticatedUser(
      @AuthenticationPrincipal String uid) throws UserDoesNotExistException {
    User user = userService.getUserById(uid);

    return ResponseEntity.status(HttpStatus.OK)
        .body(GetAuthenticatedUserResponseBodyDto.from(user));
  }

  @GetMapping("/{uid}")
  public ResponseEntity<? extends GetUserResponseBodyDto> getUserById(
      @AuthenticationPrincipal String uid, @PathVariable(name = "uid") String requestedUid)
      throws UserDoesNotExistException {
    User user = userService.getUserById(requestedUid);
    if (uid != null && uid.equals(requestedUid)) {
      return getAuthenticatedUser(uid);
    }

    return ResponseEntity.status(HttpStatus.OK).body(GetUserByIdResponseBodyDto.from(user));
  }
}
