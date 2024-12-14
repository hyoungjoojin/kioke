package com.kioke.user.controller;

import com.kioke.user.dto.request.user.CreateUserRequestBodyDto;
import com.kioke.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired @Lazy UserService userService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createUser(@RequestBody @Valid CreateUserRequestBodyDto requestBodyDto) {
    String uid = requestBodyDto.getUid(), email = requestBodyDto.getEmail();
    userService.createUser(uid, email);
  }
}
