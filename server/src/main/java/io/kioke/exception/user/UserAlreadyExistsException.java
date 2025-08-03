package io.kioke.exception.user;

import io.kioke.constant.ErrorCode;
import io.kioke.exception.KiokeException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends KiokeException {

  @Override
  public ErrorCode code() {
    return ErrorCode.USER_ALREADY_EXISTS;
  }

  @Override
  public HttpStatus status() {
    return HttpStatus.CONFLICT;
  }
}
