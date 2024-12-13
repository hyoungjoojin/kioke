package com.kioke.auth.controller;

import com.kioke.auth.dto.request.auth.LoginUserRequestBodyDto;
import com.kioke.auth.dto.request.auth.RegisterUserRequestBodyDto;
import com.kioke.auth.exception.ServiceFailedException;
import com.kioke.auth.exception.ServiceNotFoundException;
import com.kioke.auth.exception.UserAlreadyExistsException;
import com.kioke.auth.exception.UserDoesNotExistException;
import com.kioke.auth.service.AuthService;
import com.kioke.auth.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired @Lazy AuthService authService;
  @Autowired @Lazy JwtService jwtService;

  @PostMapping("/register")
  public String registerUser(@RequestBody @Valid RegisterUserRequestBodyDto requestBodyDto)
      throws UserAlreadyExistsException, ServiceNotFoundException, ServiceFailedException {
    String email = requestBodyDto.getEmail(), password = requestBodyDto.getPassword();
    String uid = authService.registerUser(email, password);

    String token = jwtService.buildToken(uid);
    return token;
  }

  @PostMapping("/login")
  public String loginUser(@RequestBody @Valid LoginUserRequestBodyDto requestBodyDto)
      throws UserDoesNotExistException, BadCredentialsException {
    String email = requestBodyDto.getEmail(), password = requestBodyDto.getPassword();
    String uid = authService.loginUser(email, password);

    String token = jwtService.buildToken(uid);
    return token;
  }
}
