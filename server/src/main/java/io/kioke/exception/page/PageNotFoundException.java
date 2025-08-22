package io.kioke.exception.page;

import io.kioke.constant.ErrorCode;
import io.kioke.exception.KiokeException;

public class PageNotFoundException extends KiokeException {

  @Override
  public ErrorCode code() {
    return ErrorCode.PAGE_NOT_FOUND;
  }
}
