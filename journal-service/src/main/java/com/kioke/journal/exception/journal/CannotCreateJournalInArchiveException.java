package com.kioke.journal.exception.journal;

import com.kioke.journal.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class CannotCreateJournalInArchiveException extends KiokeException {

  @Override
  protected HttpStatus getStatus() {
    return HttpStatus.BAD_REQUEST;
  }

  @Override
  protected String getTitle() {
    return "Cannot create journal inside archive.";
  }
}
