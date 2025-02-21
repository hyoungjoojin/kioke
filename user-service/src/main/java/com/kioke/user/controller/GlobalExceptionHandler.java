package com.kioke.user.controller;

import com.kioke.user.exception.KiokeException;
import com.kioke.user.exception.discovery.*;
import com.kioke.user.exception.friend.*;
import com.kioke.user.exception.user.*;
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
    UserDoesNotExistException.class,
    CannotAddSelfAsFriendException.class,
    FriendRelationAlreadyExistsException.class,
    FriendRequestAlreadySentException.class
  })
  public ProblemDetail kiokeExceptionHandler(KiokeException e, HttpServletRequest request) {
    URI instance = URI.create(request.getRequestURI());
    return e.intoProblemDetail(instance);
  }

  @ExceptionHandler({Exception.class, ServiceNotFoundException.class, ServiceFailedException.class})
  public ProblemDetail exceptionHandler(Exception e, HttpServletRequest request) {
    log.error(e.getMessage());
    log.info(e.getStackTrace().toString());

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR, "Unhandled internal server error has happened.");
    problemDetail.setType(URI.create("about:blank"));
    problemDetail.setTitle("Unhandled internal server error.");
    problemDetail.setInstance(URI.create(request.getRequestURI()));

    return problemDetail;
  }
}
