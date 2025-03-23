package kioke.journal.exception.permission;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class AccessDeniedException extends KiokeException {

  @Override
  protected HttpStatus getStatus() {
    return HttpStatus.FORBIDDEN;
  }

  @Override
  protected String getTitle() {
    return "Access denied.";
  }
}
