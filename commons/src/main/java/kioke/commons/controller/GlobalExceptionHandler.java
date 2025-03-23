package kioke.commons.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import kioke.commons.exception.KiokeException;
import kioke.commons.exception.security.TokenExpiredException;
import kioke.commons.exception.security.TokenInvalidException;
import kioke.commons.exception.security.TokenNotFoundException;
import kioke.commons.http.HttpResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(KiokeException.class)
  public ResponseEntity<HttpResponseBody<Void>> kiokeExceptionHandler(
      KiokeException e, HttpServletRequest request) {
    ProblemDetail problemDetail = e.intoProblemDetail(URI.create(request.getRequestURI()));

    return ResponseEntity.status(e.getStatus())
        .body(
            HttpResponseBody.error(
                (String) request.getAttribute("requestId"), e.getStatus(), problemDetail));
  }

  @ExceptionHandler({
    TokenNotFoundException.class,
    TokenInvalidException.class,
    TokenExpiredException.class,
  })
  public ResponseEntity<HttpResponseBody<Void>> securityExceptionHandler(
      Exception e, HttpServletRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN;

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "");
    problemDetail.setType(URI.create("about:blank"));
    problemDetail.setTitle("Access denied.");
    problemDetail.setInstance(URI.create(request.getRequestURI()));

    return ResponseEntity.status(status)
        .body(
            HttpResponseBody.error(
                (String) request.getAttribute("requestId"), status, problemDetail));
  }
}
