package com.kioke.journal.controller;

import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.exception.permission.AccessDeniedException;
import com.kioke.journal.exception.shelf.ShelfNotFoundException;
import com.kioke.journal.exception.user.UserNotFoundException;
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

  @ExceptionHandler(AccessDeniedException.class)
  public ProblemDetail accessDeniedExceptionHandler(Exception e, HttpServletRequest request) {
    log.debug(e.toString());

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "Access denied.");
    problemDetail.setType(URI.create("about:blank"));
    problemDetail.setTitle("Access denied.");
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    return problemDetail;
  }

  @ExceptionHandler({
    UserNotFoundException.class,
    JournalNotFoundException.class,
    ShelfNotFoundException.class
  })
  public ProblemDetail resourceNotFoundExceptionHandler(Exception e, HttpServletRequest request) {
    log.debug(e.toString());

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND, "The requested resource could not be found.");
    problemDetail.setType(URI.create("about:blank"));
    problemDetail.setTitle("The requested resource could not be found.");
    problemDetail.setInstance(URI.create(request.getRequestURI()));
    return problemDetail;
  }

  @ExceptionHandler(Exception.class)
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
