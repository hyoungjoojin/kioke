package kioke.journal.exception.user;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends KiokeException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  protected String getTitle() {
    return "User not found.";
  }
}
