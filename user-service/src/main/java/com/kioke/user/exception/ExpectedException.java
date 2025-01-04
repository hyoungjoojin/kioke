package com.kioke.user.exception;

import java.net.URI;
import org.springframework.http.HttpStatus;

public abstract class ExpectedException extends Exception {
  public ExpectedException(String message) {
    super(message);
  }

  public abstract HttpStatus getHttpStatus();

  public abstract String getTitle();

  public abstract URI getType();
}
;
