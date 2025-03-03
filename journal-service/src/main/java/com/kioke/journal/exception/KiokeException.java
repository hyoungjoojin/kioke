package com.kioke.journal.exception;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public abstract class KiokeException extends Exception {

  public KiokeException() {}

  public KiokeException(String message) {
    super(message);
  }

  protected abstract HttpStatus getStatus();

  protected URI getType() {
    return URI.create("about:blank");
  }

  protected abstract String getTitle();

  protected String getDetail() {
    return "";
  }

  public ProblemDetail intoProblemDetail(URI instance) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(getStatus(), getDetail());
    problemDetail.setType(getType());
    problemDetail.setTitle(getTitle());
    problemDetail.setInstance(instance);
    return problemDetail;
  }
}
