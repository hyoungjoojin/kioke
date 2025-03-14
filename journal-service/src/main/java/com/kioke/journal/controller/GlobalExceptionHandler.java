package com.kioke.journal.controller;

import com.kioke.journal.exception.KiokeException;
import com.kioke.journal.exception.security.ExpiredTokenException;
import com.kioke.journal.exception.security.InvalidTokenException;
import com.kioke.journal.exception.security.TokenNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({
    TokenNotFoundException.class,
    InvalidTokenException.class,
    ExpiredTokenException.class
  })
  public ProblemDetail invalidTokenExceptionHandler(Exception e) {
    return ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(KiokeException.class)
  public ProblemDetail kiokeExceptionHandler(KiokeException e, HttpServletRequest request) {
    log.debug(e.toString());

    return e.intoProblemDetail(URI.create(request.getRequestURI()));
  }

  @ExceptionHandler({IllegalStateException.class, Exception.class})
  public ProblemDetail globalExceptionHandler(Exception e, HttpServletRequest request) {
    log.error(e.toString());
    log.debug(e.getMessage());

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "An unhandled internal server error has happened.");
    problemDetail.setType(URI.create("about:blank"));
    problemDetail.setTitle("An unhandled internal server error.");
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    return problemDetail;
  }
}
