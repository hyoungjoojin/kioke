package com.kioke.journal.exception.user;

import com.kioke.journal.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends KiokeException {

  @Override
  protected HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  protected String getTitle() {
    return "User not found.";
  }
}
