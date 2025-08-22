package io.kioke.exception;

import io.kioke.constant.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public abstract class KiokeException extends Exception {

  public abstract ErrorCode code();

  public String details() {
    return "";
  }

  public HttpStatus status() {
    return code().status();
  }

  public ProblemDetail problemDetail() {
    ProblemDetail problemDetail = code().problemDetail();
    if (!details().isEmpty()) {
      problemDetail.setDetail(details());
    }

    return problemDetail;
  }

  @Override
  public String toString() {
    return code().toString() + (details().isEmpty() ? "" : ", ") + details();
  }
}
