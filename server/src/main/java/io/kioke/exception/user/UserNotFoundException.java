package io.kioke.exception.user;

import io.kioke.constant.ErrorCode;
import io.kioke.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends KiokeException {

  @Override
  public ErrorCode code() {
    return ErrorCode.USER_NOT_FOUND;
  }

  @Override
  public HttpStatus status() {
    return HttpStatus.NOT_FOUND;
  }
}
