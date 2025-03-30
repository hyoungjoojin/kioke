package kioke.user.exception.friend;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class CannotAddSelfAsFriendException extends KiokeException {

  public CannotAddSelfAsFriendException() {
    super("Cannot add self as friend.");
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.INVALID_FRIEND_REQUEST;
  }
}
