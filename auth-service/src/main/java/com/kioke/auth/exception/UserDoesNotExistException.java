package com.kioke.auth.exception;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class UserDoesNotExistException extends KiokeException {

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.USER_NOT_FOUND;
  }
}
