package kioke.user.feature.user.exception;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class UserNotFoundException extends KiokeException {

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.USER_NOT_FOUND;
  }
}
