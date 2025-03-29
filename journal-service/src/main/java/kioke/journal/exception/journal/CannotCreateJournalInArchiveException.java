package kioke.journal.exception.journal;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class CannotCreateJournalInArchiveException extends KiokeException {

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.CANNOT_CREATE_JOURNAL;
  }
}
