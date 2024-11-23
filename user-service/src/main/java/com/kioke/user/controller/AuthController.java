package com.kioke.user.controller;

import com.kioke.user.dto.data.auth.RegisterUserDto;
import com.kioke.user.dto.request.auth.*;
import com.kioke.user.dto.response.ResponseDto;
import com.kioke.user.dto.response.data.auth.LoginUserResponseDataDto;
import com.kioke.user.dto.response.data.auth.RegisterUserResponseDataDto;
import com.kioke.user.exception.user.UserNotFoundException;
import com.kioke.user.model.User;
import com.kioke.user.service.AuthService;
import com.kioke.user.service.JwtService;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired @Lazy private AuthService authService;
  @Autowired @Lazy private JwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<ResponseDto<RegisterUserResponseDataDto>> registerUser(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      @RequestBody @Valid RegisterUserRequestBodyDto requestBody) {
    HttpStatus status = HttpStatus.CREATED;

    User user = authService.registerUser(RegisterUserDto.from(requestBody));
    RegisterUserResponseDataDto data = RegisterUserResponseDataDto.from(user);

    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            ResponseDto.<RegisterUserResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(status.value())
                .success(true)
                .data(Optional.of(data))
                .error(Optional.empty())
                .build());
  }

  @PostMapping("/login")
  public ResponseEntity<ResponseDto<LoginUserResponseDataDto>> loginUser(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      @RequestBody @Valid LoginUserRequestBodyDto requestBody)
      throws UserNotFoundException {
    HttpStatus status = HttpStatus.CREATED;

    User user = authService.authenticateUser(requestBody.getEmail(), requestBody.getPassword());

    String token = jwtService.buildToken(user);
    LoginUserResponseDataDto data = LoginUserResponseDataDto.builder().token(token).build();

    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            ResponseDto.<LoginUserResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(status.value())
                .success(true)
                .data(Optional.of(data))
                .error(Optional.empty())
                .build());
  }
}
