package kioke.journal.exception.shelf;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class ShelfNotFoundException extends KiokeException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  protected String getTitle() {
    return "Shelf not found.";
  }
}
