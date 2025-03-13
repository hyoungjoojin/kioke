package com.kioke.user.controller;

import com.kioke.user.dto.data.user.CreateUserDto;
import com.kioke.user.dto.request.user.CreateUserRequestBodyDto;
import com.kioke.user.dto.response.data.user.GetUserResponseBodyDto;
import com.kioke.user.dto.response.data.user.GetUserResponseBodyDto.*;
import com.kioke.user.exception.discovery.ServiceFailedException;
import com.kioke.user.exception.discovery.ServiceNotFoundException;
import com.kioke.user.exception.user.UserDoesNotExistException;
import com.kioke.user.model.User;
import com.kioke.user.service.UserService;
import jakarta.validation.Valid;
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

  @PostMapping
  public ResponseEntity<Void> createUser(
      @RequestBody @Valid CreateUserRequestBodyDto requestBodyDto)
      throws ServiceNotFoundException, ServiceFailedException {
    userService.createUser(CreateUserDto.from(requestBodyDto));

    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

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
