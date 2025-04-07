package com.kioke.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import kioke.commons.constant.ErrorCode;
import kioke.commons.controller.AbstractGlobalExceptionHandler;
import kioke.commons.http.HttpResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractGlobalExceptionHandler {

  @ExceptionHandler({BadCredentialsException.class})
  public ResponseEntity<HttpResponseBody<Void>> badCredentialsExceptionHandler(
      Exception e, HttpServletRequest request) {
    ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;

    return ResponseEntity.status(errorCode.getStatus())
        .body(HttpResponseBody.error(request, errorCode));
  }
}
