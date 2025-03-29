package com.kioke.auth.controller;

import com.kioke.auth.dto.data.auth.RegisterUserDto;
import com.kioke.auth.dto.request.auth.LoginUserRequestBodyDto;
import com.kioke.auth.dto.request.auth.RegisterUserRequestBodyDto;
import com.kioke.auth.dto.response.auth.LoginUserResponseBodyDto;
import com.kioke.auth.dto.response.auth.RegisterUserResponseBodyDto;
import com.kioke.auth.exception.UserAlreadyExistsException;
import com.kioke.auth.exception.UserDoesNotExistException;
import com.kioke.auth.model.User;
import com.kioke.auth.service.AuthService;
import com.kioke.auth.service.JwtService;
import com.kioke.auth.service.message.producer.UserRegistrationMessageProducerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kioke.commons.dto.message.UserRegistrationMessageDto;
import kioke.commons.http.HttpResponseBody;
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
  @Autowired UserRegistrationMessageProducerService userRegistrationMessageProducerService;
  @Autowired @Lazy JwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<HttpResponseBody<RegisterUserResponseBodyDto>> registerUser(
      @RequestBody @Valid RegisterUserRequestBodyDto requestBodyDto, HttpServletRequest request)
      throws UserAlreadyExistsException {
    User user = authService.registerUser(RegisterUserDto.from(requestBodyDto));
    userRegistrationMessageProducerService.send(
        new UserRegistrationMessageDto(
            user.getUid(),
            requestBodyDto.getEmail(),
            requestBodyDto.getFirstName(),
            requestBodyDto.getLastName()));

    String accessToken = jwtService.buildToken(user.getUid());

    HttpStatus status = HttpStatus.CREATED;
    RegisterUserResponseBodyDto data =
        RegisterUserResponseBodyDto.builder().uid(user.getUid()).accessToken(accessToken).build();

    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, data));
  }

  @PostMapping("/login")
  public ResponseEntity<HttpResponseBody<LoginUserResponseBodyDto>> loginUser(
      @RequestBody @Valid LoginUserRequestBodyDto requestBodyDto, HttpServletRequest request)
      throws UserDoesNotExistException, BadCredentialsException {
    String email = requestBodyDto.getEmail(), password = requestBodyDto.getPassword();
    String uid = authService.loginUser(email, password).getUid();

    String accessToken = jwtService.buildToken(uid);

    HttpStatus status = HttpStatus.CREATED;
    LoginUserResponseBodyDto data =
        LoginUserResponseBodyDto.builder().uid(uid).accessToken(accessToken).build();

    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, data));
  }
}
