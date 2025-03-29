package kioke.journal.exception.journal;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class JournalNotFoundException extends KiokeException {

  public JournalNotFoundException(String journalId) {
    super("Journal with ID " + journalId + " cannot be found.");
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.JOURNAL_NOT_FOUND;
  }
}
