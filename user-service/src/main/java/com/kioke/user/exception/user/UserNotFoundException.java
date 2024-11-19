package com.kioke.user.exception.user;

public class UserNotFoundException extends Exception {
  private String email;

  public UserNotFoundException(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    if (email == null) {
      return "Requested user email is null.";
    }

    return "User with email " + email + " could not be found.";
  }
}
