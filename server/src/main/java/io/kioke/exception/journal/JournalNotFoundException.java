package io.kioke.exception.journal;

import io.kioke.constant.ErrorCode;
import io.kioke.exception.KiokeException;

public class JournalNotFoundException extends KiokeException {

  @Override
  public ErrorCode code() {
    return ErrorCode.JOURNAL_NOT_FOUND;
  }
}
