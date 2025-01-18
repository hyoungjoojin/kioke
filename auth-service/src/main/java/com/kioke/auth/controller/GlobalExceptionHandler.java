package com.kioke.auth.controller;

import com.kioke.auth.exception.ExpectedException;
import com.kioke.auth.exception.ServiceFailedException;
import com.kioke.auth.exception.ServiceNotFoundException;
import com.kioke.auth.exception.UserAlreadyExistsException;
import com.kioke.auth.exception.UserDoesNotExistException;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({UserDoesNotExistException.class, UserAlreadyExistsException.class})
  public ProblemDetail expectedExceptionHandler(ExpectedException e, HttpServletRequest request) {
    log.info(e.getMessage());

    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(e.getHttpStatus(), e.getMessage());
    problemDetail.setType(e.getType());
    problemDetail.setTitle(e.getTitle());
    problemDetail.setInstance(URI.create(request.getRequestURI()));

    return problemDetail;
  }

  @ExceptionHandler({BadCredentialsException.class})
  public ProblemDetail badCredentialsExceptionHandler(Exception e, HttpServletRequest request) {
    ProblemDetail problemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
    problemDetail.setType(URI.create("about:blank"));
    problemDetail.setTitle("Bad credentials");
    problemDetail.setInstance(URI.create(request.getRequestURI()));

    return problemDetail;
  }

  @ExceptionHandler({ServiceNotFoundException.class, ServiceFailedException.class, Exception.class})
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
