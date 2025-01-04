package com.kioke.user.exception;

import java.net.URI;
import org.springframework.http.HttpStatus;

public class UserDoesNotExistException extends ExpectedException {
  public static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
  public static final String TITLE = "User does not exist.";
  public static final URI TYPE = URI.create("users/does-not-exist");

  @Override
  public HttpStatus getHttpStatus() {
    return HTTP_STATUS;
  }

  @Override
  public String getTitle() {
    return TITLE;
  }

  @Override
  public URI getType() {
    return TYPE;
  }

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
