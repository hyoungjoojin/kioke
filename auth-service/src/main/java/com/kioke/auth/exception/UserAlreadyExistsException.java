package com.kioke.auth.exception;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends KiokeException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.CONFLICT;
  }

  @Override
  protected String getTitle() {
    return "User already exists.";
  }
}
