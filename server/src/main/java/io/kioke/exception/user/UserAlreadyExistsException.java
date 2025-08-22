package io.kioke.exception.user;

import io.kioke.constant.ErrorCode;
import io.kioke.exception.KiokeException;

public class UserAlreadyExistsException extends KiokeException {

  @Override
  public ErrorCode code() {
    return ErrorCode.USER_ALREADY_EXISTS;
  }
}
