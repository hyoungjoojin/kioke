package com.kioke.journal.exception.journal;

import com.kioke.journal.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class JournalNotFoundException extends KiokeException {

  @Override
  protected HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  protected String getTitle() {
    return "Journal not founc.";
  }
}
