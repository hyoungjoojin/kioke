package com.kioke.journal.exception.shelf;

import com.kioke.journal.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class ShelfNotFoundException extends KiokeException {

  @Override
  protected HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  protected String getTitle() {
    return "Shelf not found.";
  }
}
