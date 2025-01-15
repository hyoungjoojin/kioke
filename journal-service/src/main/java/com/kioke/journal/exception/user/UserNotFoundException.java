package com.kioke.journal.exception.user;

public class UserNotFoundException extends Exception {

  public UserNotFoundException(String uid) {
    super("User with ID " + uid + " could not be found.");
  }
}
