package kioke.commons.constant;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
  USER_NOT_FOUND(
      HttpStatus.NOT_FOUND, "U-0001", "User Not Found", "The requested user could not be found."),
  USER_ALREADY_EXISTS(
      HttpStatus.CONFLICT,
      "U-0002",
      "User Already Exists",
      "A user with the given information already exists."),
  INVALID_CREDENTIALS(
      HttpStatus.UNAUTHORIZED,
      "U-0003",
      "Invalid Credentials",
      "The given credentials are incorrect."),
  NO_ACCESS_TOKEN(
      HttpStatus.BAD_REQUEST,
      "U-0004",
      "No Access Token",
      "No access token is provided in the request."),
  INVALID_ACCESS_TOKEN(
      HttpStatus.UNAUTHORIZED,
      "U-0005",
      "Invalid Access Token",
      "The provided access token is invalid or may be expired."),
  FRIEND_REQUEST_ALREADY_SENT(
      HttpStatus.CONFLICT,
      "U-0006",
      "Friend Request Already Sent",
      "Friend request is already sent."),
  INVALID_FRIEND_REQUEST(
      HttpStatus.BAD_REQUEST,
      "U-0007",
      "Invalid Friend Request",
      "The friend request cannot be processed since the request is invalid. "),

  JOURNAL_NOT_FOUND(
      HttpStatus.NOT_FOUND,
      "J-0001",
      "Journal Not Found",
      "The requested journal could not be found."),
  SHELF_NOT_FOUND(
      HttpStatus.NOT_FOUND, "J-0002", "Shelf Not Found", "The requested shelf could not be found."),
  CANNOT_CREATE_JOURNAL(
      HttpStatus.BAD_REQUEST,
      "J-0003",
      "Cannot Create Journal In Archive",
      "Journals cannot be created inside archive shelves."),
  NO_EDIT_PERMISSIONS(
      HttpStatus.FORBIDDEN,
      "J-0004",
      "No Edit Permissions",
      "You do not have the necessary permissions to edit this resource."),
  NO_DELETE_PERMISSIONS(
      HttpStatus.FORBIDDEN,
      "J-0005",
      "No Delete Permissions",
      "You do not have the necessary permissions to delete this resource."),

  INTERNAL_SERVER_ERROR(
      HttpStatus.INTERNAL_SERVER_ERROR,
      "E-0000",
      "Internal Server Error",
      "An unknown error occurred on the server.");

  private HttpStatus status;
  private String code;
  private String title;
  private String message;

  private ErrorCode(HttpStatus status, String code, String title, String message) {
    this.status = status;
    this.code = code;
    this.title = title;
    this.message = message;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getTitle() {
    return title;
  }

  public String getMessage() {
    return message;
  }
}
