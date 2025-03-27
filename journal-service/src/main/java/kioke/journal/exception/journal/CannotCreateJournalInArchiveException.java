package kioke.journal.exception.journal;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class CannotCreateJournalInArchiveException extends KiokeException {

  @Override
  public HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  @Override
  protected String getTitle() {
    return "Cannot create journal inside archive.";
  }
}
