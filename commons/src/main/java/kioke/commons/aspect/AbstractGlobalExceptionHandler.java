package kioke.commons.aspect;

import jakarta.servlet.http.HttpServletRequest;
import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;
import kioke.commons.exception.security.TokenExpiredException;
import kioke.commons.exception.security.TokenInvalidException;
import kioke.commons.exception.security.TokenNotFoundException;
import kioke.commons.http.HttpResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
public abstract class AbstractGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(KiokeException.class)
  public ResponseEntity<HttpResponseBody<Void>> kiokeExceptionHandler(
      KiokeException e, HttpServletRequest request) {
    ErrorCode errorCode = e.getErrorCode();

    return ResponseEntity.status(errorCode.getStatus())
        .body(HttpResponseBody.error(request, errorCode, e.getDetail()));
  }

  @ExceptionHandler({
    TokenNotFoundException.class,
    TokenInvalidException.class,
    TokenExpiredException.class,
  })
  public ResponseEntity<HttpResponseBody<Void>> tokenNotFoundExceptionHandler(
      Exception e, HttpServletRequest request) {
    ErrorCode errorCode = ErrorCode.INVALID_ACCESS_TOKEN;

    return ResponseEntity.status(errorCode.getStatus())
        .body(HttpResponseBody.error(request, errorCode));
  }

  @ExceptionHandler({UsernameNotFoundException.class})
  public ResponseEntity<HttpResponseBody<Void>> usernameNotFoundExceptionHandler(
      Exception e, HttpServletRequest request) {
    ErrorCode errorCode = ErrorCode.USER_NOT_FOUND;

    return ResponseEntity.status(errorCode.getStatus())
        .body(HttpResponseBody.error(request, errorCode, e.getMessage()));
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<HttpResponseBody<Void>> exceptionHandler(
      Exception e, HttpServletRequest request) {
    ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

    log.error("An unknown error has occurred.", e);

    return ResponseEntity.status(errorCode.getStatus())
        .body(HttpResponseBody.error(request, errorCode));
  }
}
