package com.kioke.user.exception.friend;

import com.kioke.user.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class FriendRelationAlreadyExistsException extends KiokeException {
  @Override
  protected HttpStatus getStatus() {
    return HttpStatus.CONFLICT;
  }

  @Override
  protected String getTitle() {
    return "Friend relationship already exists.";
  }
}
