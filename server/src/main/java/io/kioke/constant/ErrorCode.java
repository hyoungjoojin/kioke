package io.kioke.constant;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public enum ErrorCode {
  INTERNAL_SERVER_ERROR(
      "INTERNAL_SERVER_ERROR", "Internal Server Error", "", HttpStatus.INTERNAL_SERVER_ERROR),

  BAD_REQUEST("BAD_REQUEST", "Bad Request", "", HttpStatus.BAD_REQUEST),

  ACCESS_DENIED("ACCESS_DENIED", "Access Denied", "", HttpStatus.FORBIDDEN),

  USER_NOT_FOUND("USER_NOT_FOUND", "User Not Found", "", HttpStatus.NOT_FOUND),

  USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User Already Exists", "", HttpStatus.CONFLICT),

  BAD_CREDENTIALS("BAD_CREDENTIALS", "Bad Credentials", "", HttpStatus.UNAUTHORIZED),

  JOURNAL_NOT_FOUND("JOURNAL_NOT_FOUND", "Journal Not Found", "", HttpStatus.NOT_FOUND),

  COLLECTION_NOT_FOUND(
      "COLLECTION_NOT_FOUND", "Journal Collection Not Found", "", HttpStatus.NOT_FOUND),

  PAGE_NOT_FOUND("PAGE_NOT_FOUND", "Page Not Found", "", HttpStatus.NOT_FOUND);

  private String code;
  private String title;
  private String type;
  private HttpStatus status;

  private ErrorCode(String code, String title, String type, HttpStatus status) {
    this.code = code;
    this.title = title;
    this.type = type.isEmpty() ? "about:blank" : type;
    this.status = status;
  }

  public HttpStatus status() {
    return status;
  }

  public ProblemDetail problemDetail() {
    ProblemDetail detail = ProblemDetail.forStatus(status);
    detail.setTitle(title);
    detail.setType(URI.create(type));
    detail.setProperty("code", code);
    return detail;
  }

  @Override
  public String toString() {
    return String.format("[{%s}]: {%s}", code, title);
  }
}
