package kioke.user.exception.friend;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class CannotAddSelfAsFriendException extends KiokeException {

  @Override
  protected HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  @Override
  protected String getTitle() {
    return "Cannot add self as friend.";
  }
}
