package kioke.user.exception.friend;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class FriendRelationAlreadyExistsException extends KiokeException {

  public FriendRelationAlreadyExistsException() {
    super("Friend relationship already exists.");
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.INVALID_FRIEND_REQUEST;
  }
}
