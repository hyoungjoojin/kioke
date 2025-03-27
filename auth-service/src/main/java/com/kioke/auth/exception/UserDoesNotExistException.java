package com.kioke.auth.exception;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class UserDoesNotExistException extends KiokeException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  protected String getTitle() {
    return "User does not exist.";
  }
}
