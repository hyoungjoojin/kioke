package com.kioke.user.constant;

import com.kioke.user.exception.user.UserNotFoundException;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

public enum ErrorCode {
  INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "about:blank", "Invalid request body."),
  USER_NOT_FOUND(
      HttpStatus.NOT_FOUND, "errors/user-not-found", "The requested user could not be found."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "about:blank", "Internal server error");

  HttpStatus status;
  String type;
  String title;

  private ErrorCode(HttpStatus status, String type, String title) {
    this.status = status;
    this.type = type;
    this.title = title;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public URI getType() {
    return URI.create(type);
  }

  public String getTitle() {
    return title;
  }

  public static ErrorCode getErrorCodeFromException(Exception e) {
    Class<? extends Exception> exceptionClass = e.getClass();

    if (exceptionClass == MethodArgumentNotValidException.class) {
      return ErrorCode.INVALID_REQUEST_BODY;
    } else if (exceptionClass == UserNotFoundException.class) {
      return ErrorCode.USER_NOT_FOUND;
    } else {
      return ErrorCode.INTERNAL_SERVER_ERROR;
    }
  }
}
