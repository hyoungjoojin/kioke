package com.kioke.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import kioke.commons.controller.AbstractGlobalExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {

  @ExceptionHandler({BadCredentialsException.class})
  public ProblemDetail badCredentialsExceptionHandler(
      BadCredentialsException e, HttpServletRequest request) {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    problemDetail.setType(URI.create("about:blank"));
    problemDetail.setTitle("Bad credentials");
    problemDetail.setInstance(URI.create(request.getRequestURI()));

    return problemDetail;
  }
}
