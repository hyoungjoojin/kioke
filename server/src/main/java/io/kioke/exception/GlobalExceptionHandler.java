package io.kioke.exception;

import io.kioke.constant.ErrorCode;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(KiokeException.class)
  protected ResponseEntity<ProblemDetail> handleKiokeException(KiokeException e) {
    logger.debug("An exception has occured: {}", e);

    return ResponseEntity.status(e.status())
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(e.problemDetail());
  }

  @ExceptionHandler(BadCredentialsException.class)
  protected ResponseEntity<ProblemDetail> handleAuthenticationException(AuthenticationException e) {
    ErrorCode code = ErrorCode.BAD_CREDENTIALS;

    return ResponseEntity.status(code.status())
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(code.problemDetail());
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ProblemDetail> globalExceptionHandler(Exception e) {
    ErrorCode code = ErrorCode.INTERNAL_SERVER_ERROR;
    logger.error("An unknown exception has occured", e);

    ProblemDetail detail = code.problemDetail();
    detail.setDetail(e.getMessage());

    return ResponseEntity.status(code.status())
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(detail);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    ErrorCode code = ErrorCode.BAD_REQUEST;

    ProblemDetail detail = code.problemDetail();
    detail.setDetail(
        ex.getAllErrors().stream()
            .map(
                error -> {
                  return error.getDefaultMessage();
                })
            .collect(Collectors.joining(", ")));

    return ResponseEntity.status(code.status()).body(detail);
  }
}
