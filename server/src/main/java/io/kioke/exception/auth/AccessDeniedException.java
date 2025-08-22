package io.kioke.exception.auth;

import io.kioke.constant.ErrorCode;
import io.kioke.exception.KiokeException;

public class AccessDeniedException extends KiokeException {

  @Override
  public ErrorCode code() {
    return ErrorCode.ACCESS_DENIED;
  }
}
