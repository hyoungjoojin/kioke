package kioke.journal.exception.permission;

import kioke.commons.constant.ErrorCode;
import kioke.commons.exception.KiokeException;

public class NoEditPermissionsException extends KiokeException {

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.NO_EDIT_PERMISSIONS;
  }
}
