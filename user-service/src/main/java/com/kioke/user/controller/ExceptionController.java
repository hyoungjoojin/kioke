package com.kioke.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ProblemDetail> exceptionHandler(Exception e, HttpServletRequest request) {
    URI type = URI.create("about:blank");
    String title = "Unhandled internal server error.";
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String detail = e.getMessage();
    String instance = request.getRequestURI();

    return buildResponseEntity(type, title, status, detail, instance);
  }

  private static ResponseEntity<ProblemDetail> buildResponseEntity(
      URI type, String title, HttpStatus status, String detail, String instance) {
    ProblemDetail body = ProblemDetail.forStatusAndDetail(status, detail);
    body.setType(type);
    body.setTitle(title);
    body.setInstance(URI.create(instance));

    return ResponseEntity.status(status).contentType(MediaType.APPLICATION_PROBLEM_JSON).body(body);
  }
}
