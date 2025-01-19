package com.kioke.auth.controller;

import com.kioke.auth.dto.data.auth.RegisterUserDto;
import com.kioke.auth.dto.request.auth.LoginUserRequestBodyDto;
import com.kioke.auth.dto.request.auth.RegisterUserRequestBodyDto;
import com.kioke.auth.dto.response.auth.LoginUserResponseBodyDto;
import com.kioke.auth.dto.response.auth.RegisterUserResponseBodyDto;
import com.kioke.auth.exception.ServiceNotFoundException;
import com.kioke.auth.exception.UserAlreadyExistsException;
import com.kioke.auth.exception.UserDoesNotExistException;
import com.kioke.auth.service.AuthService;
import com.kioke.auth.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/login")
  public ResponseEntity<LoginUserResponseBodyDto> loginUser(
      @RequestBody @Valid LoginUserRequestBodyDto requestBodyDto)
      throws UserDoesNotExistException, BadCredentialsException {
    String email = requestBodyDto.getEmail(), password = requestBodyDto.getPassword();
    String uid = authService.loginUser(email, password).getUid();

    String accessToken = jwtService.buildToken(uid);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(LoginUserResponseBodyDto.builder().uid(uid).accessToken(accessToken).build());
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterUserResponseBodyDto> registerUser(
      @RequestBody @Valid RegisterUserRequestBodyDto requestBodyDto)
      throws UserAlreadyExistsException, ServiceNotFoundException, Exception {
    String uid = authService.registerUser(RegisterUserDto.from(requestBodyDto)).getUid();

    String accessToken = jwtService.buildToken(uid);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(RegisterUserResponseBodyDto.builder().uid(uid).accessToken(accessToken).build());
  }
}
