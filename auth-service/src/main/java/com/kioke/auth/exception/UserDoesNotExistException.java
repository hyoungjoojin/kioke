package com.kioke.auth.exception;

public class UserDoesNotExistException extends Exception {
  public static enum UserIdentifierType {
    UID,
    EMAIL;

    public static String getErrorMessage(
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

  public UserDoesNotExistException(UserIdentifierType userIdentifierType, String userIdentifier) {
    super(UserIdentifierType.getErrorMessage(userIdentifierType, userIdentifier));
  }
}
