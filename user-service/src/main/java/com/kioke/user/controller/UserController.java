package com.kioke.user.controller;

import com.kioke.user.dto.data.user.CreateUserDto;
import com.kioke.user.dto.request.user.CreateUserRequestBodyDto;
import com.kioke.user.dto.response.data.user.GetUserResponseBodyDto;
import com.kioke.user.dto.response.data.user.GetUserResponseBodyDto.*;
import com.kioke.user.exception.UserDoesNotExistException;
import com.kioke.user.model.User;
import com.kioke.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired @Lazy UserService userService;

  @PostMapping
  public ResponseEntity<Void> createUser(
      @RequestBody @Valid CreateUserRequestBodyDto requestBodyDto) {
    userService.createUser(CreateUserDto.from(requestBodyDto));

    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }

  @GetMapping
  public ResponseEntity<GetAuthenticatedUserResponseBodyDto> getAuthenticatedUser(
      @RequestAttribute(required = true, name = "uid") String requesterUid)
      throws UserDoesNotExistException {
    User user = userService.getUserById(requesterUid);

    return ResponseEntity.status(HttpStatus.OK)
        .body(GetAuthenticatedUserResponseBodyDto.from(user));
  }

  @GetMapping("/{uid}")
  public ResponseEntity<? extends GetUserResponseBodyDto> getUserById(
      @RequestAttribute(required = false, name = "uid") String requesterUid,
      @PathVariable(name = "uid") String requestedUid)
      throws UserDoesNotExistException {
    User user = userService.getUserById(requestedUid);
    if (requesterUid != null && requesterUid.equals(requestedUid)) {
      return getAuthenticatedUser(requesterUid);
    }

    return ResponseEntity.status(HttpStatus.OK).body(GetUserByIdResponseBodyDto.from(user));
  }
}
