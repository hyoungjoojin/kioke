package com.kioke.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<String> handleException(Exception e) {
    HttpStatus status = GlobalExceptionHandler.getStatusFromException(e);
    log.error(e.toString());
    return ResponseEntity.status(status).body(e.toString());
  }

  private static HttpStatus getStatusFromException(Exception e) {
    return HttpStatus.INTERNAL_SERVER_ERROR;
  }
}
