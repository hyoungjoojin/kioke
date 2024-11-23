package com.kioke.journal.constant;

import com.kioke.journal.exception.journal.JournalNotFoundException;
import java.net.URI;
import java.time.format.DateTimeParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

public enum ErrorCode {
  RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "about:blank", "Resource not found"),
  INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "about:blank", "Invalid request body."),
  JOURNAL_NOT_FOUND(
      HttpStatus.NOT_FOUND,
      "/errors/journal-not-found",
      "The requested journal could not be found."),
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

    if (exceptionClass == NoResourceFoundException.class) {
      return ErrorCode.RESOURCE_NOT_FOUND;
    } else if (exceptionClass == MethodArgumentNotValidException.class
        || exceptionClass == DateTimeParseException.class) {
      return ErrorCode.INVALID_REQUEST_BODY;
    } else if (exceptionClass == JournalNotFoundException.class) {
      return ErrorCode.JOURNAL_NOT_FOUND;
    } else {
      return ErrorCode.INTERNAL_SERVER_ERROR;
    }
  }
}
