package kioke.commons.exception.security;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class AccessDeniedException extends KiokeException {

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.ACCESS_DENIED;
  }
}
