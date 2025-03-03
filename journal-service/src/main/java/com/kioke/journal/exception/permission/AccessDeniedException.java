package com.kioke.journal.exception.permission;

import com.kioke.journal.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class AccessDeniedException extends KiokeException {

  @Override
  protected HttpStatus getStatus() {
    return HttpStatus.FORBIDDEN;
  }

  @Override
  protected String getTitle() {
    return "Access denied.";
  }
}
