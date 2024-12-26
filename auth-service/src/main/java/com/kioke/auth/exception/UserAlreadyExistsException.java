package com.kioke.auth.exception;

import java.net.URI;

public class UserAlreadyExistsException extends Exception {
  public static final URI TYPE = URI.create("users/already-exists");

  public UserAlreadyExistsException(String email) {
    super("User with email " + email + " already exists.");
  }
}
