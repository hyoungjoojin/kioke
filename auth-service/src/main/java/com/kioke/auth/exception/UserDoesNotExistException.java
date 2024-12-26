package com.kioke.auth.exception;

import java.net.URI;

public class UserDoesNotExistException extends Exception {
  public static final URI TYPE = URI.create("users/does-not-exist");

  public static enum UserIdentifierType {
    UID,
    EMAIL
  }

  public UserDoesNotExistException(UserIdentifierType userIdentifierType, String userIdentifier) {
    super(getErrorMessage(userIdentifierType, userIdentifier));
  }

  private static String getErrorMessage(
      UserIdentifierType userIdentifierType, String userIdentifier) {
    switch (userIdentifierType) {
      case UID:
        return "User with ID " + userIdentifier + " does not exist.";

      case EMAIL:
        return "User with email " + userIdentifier + " does not exist.";

      default:
        return "";
    }
  }
}
