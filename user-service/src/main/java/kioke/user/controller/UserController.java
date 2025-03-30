package kioke.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import kioke.commons.http.HttpResponseBody;
import kioke.user.dto.response.data.user.GetUserResponseBodyDto;
import kioke.user.dto.response.data.user.GetUserResponseBodyDto.*;
import kioke.user.model.User;
import kioke.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired @Lazy UserService userService;

  @GetMapping
  public ResponseEntity<HttpResponseBody<GetAuthenticatedUserResponseBodyDto>> getAuthenticatedUser(
      @AuthenticationPrincipal String uid, HttpServletRequest request)
      throws UsernameNotFoundException {
    User user = userService.getUserById(uid);

    HttpStatus status = HttpStatus.OK;
    return ResponseEntity.status(status)
        .body(
            HttpResponseBody.success(
                request, status, GetAuthenticatedUserResponseBodyDto.from(user)));
  }

  @GetMapping("/{uid}")
  public ResponseEntity<HttpResponseBody<? extends GetUserResponseBodyDto>> getUserById(
      @AuthenticationPrincipal String uid,
      @PathVariable(name = "uid") String requestedUid,
      HttpServletRequest request)
      throws UsernameNotFoundException {
    User user = userService.getUserById(requestedUid);

    HttpStatus status = HttpStatus.OK;
    return ResponseEntity.status(status)
        .body(HttpResponseBody.success(request, status, GetUserByIdResponseBodyDto.from(user)));
  }
}
