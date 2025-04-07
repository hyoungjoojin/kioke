package kioke.journal.exception.shelf;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class ShelfNotFoundException extends KiokeException {

  public ShelfNotFoundException(String shelfId) {
    super("Shelf with ID " + shelfId + " cannot be found.");
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.SHELF_NOT_FOUND;
  }
}
