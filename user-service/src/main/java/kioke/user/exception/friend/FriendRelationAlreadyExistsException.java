package kioke.user.exception.friend;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class FriendRelationAlreadyExistsException extends KiokeException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.CONFLICT;
  }

  @Override
  protected String getTitle() {
    return "Friend relationship already exists.";
  }
}
