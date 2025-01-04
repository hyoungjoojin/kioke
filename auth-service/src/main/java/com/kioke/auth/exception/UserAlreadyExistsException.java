package com.kioke.auth.exception;

import java.net.URI;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ExpectedException {
  public static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;
  public static final String TITLE = "User already exists.";
  public static final URI TYPE = URI.create("users/already-exists");

  @Override
  public HttpStatus getHttpStatus() {
    return HTTP_STATUS;
  }

  @Override
  public String getTitle() {
    return TITLE;
  }

  @Override
  public URI getType() {
    return TYPE;
  }

  public UserAlreadyExistsException(String email) {
    super("User with email " + email + " already exists.");
  }
}
