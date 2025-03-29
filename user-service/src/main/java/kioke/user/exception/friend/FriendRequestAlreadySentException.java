package kioke.user.exception.friend;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class FriendRequestAlreadySentException extends KiokeException {

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.FRIEND_REQUEST_ALREADY_SENT;
  }
}
