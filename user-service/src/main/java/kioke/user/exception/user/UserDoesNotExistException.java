package kioke.user.exception.user;

import kioke.commons.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class UserDoesNotExistException extends KiokeException {
  @Override
  public HttpStatus getStatus() {
    return HttpStatus.NOT_FOUND;
  }

  @Override
  protected String getTitle() {
    return "User does not exist.";
  }

  public UserDoesNotExistException(UserIdentifierType userIdentifierType, String userIdentifier) {
    super(getErrorMessage(userIdentifierType, userIdentifier));
  }

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

  public static enum UserIdentifierType {
    UID,
    EMAIL
  }
}
