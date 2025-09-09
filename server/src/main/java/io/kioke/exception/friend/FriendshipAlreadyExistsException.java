package io.kioke.exception.friend;

import io.kioke.constant.ErrorCode;
import io.kioke.exception.KiokeException;

public class FriendshipAlreadyExistsException extends KiokeException {

  @Override
  public ErrorCode code() {
    return null;
  }
}
