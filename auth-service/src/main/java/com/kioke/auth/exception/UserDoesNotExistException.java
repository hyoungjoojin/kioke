package com.kioke.auth.exception;

public class UserDoesNotExistException extends Exception {

  public UserDoesNotExistException(String email) {
    super("User with email " + email + " does not exist.");
  }
}
