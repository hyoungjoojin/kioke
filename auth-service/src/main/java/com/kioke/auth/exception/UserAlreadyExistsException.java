package com.kioke.auth.exception;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class UserAlreadyExistsException extends KiokeException {

  public UserAlreadyExistsException(String email) {
    super("User with email " + email + " already exists.");
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.USER_ALREADY_EXISTS;
  }
}
