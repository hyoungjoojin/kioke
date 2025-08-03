package io.kioke.exception;

import io.kioke.constant.ErrorCode;
import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public abstract class KiokeException extends Exception {

  public abstract ErrorCode code();

  public abstract HttpStatus status();

  public String details() {
    return "";
  }

  public ProblemDetail problemDetail() {
    ProblemDetail detail = ProblemDetail.forStatus(status());
    detail.setTitle(code().getTitle());
    detail.setType(URI.create(code().getType()));
    detail.setProperty("code", code().getKey());

    if (details() != null && !details().isEmpty()) {
      detail.setDetail(details());
    }

    return detail;
  }
}
