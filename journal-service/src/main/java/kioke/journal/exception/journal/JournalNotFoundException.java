package kioke.journal.exception.journal;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class JournalNotFoundException extends KiokeException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  protected String getTitle() {
    return "Journal not found.";
  }
}
