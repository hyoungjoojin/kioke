package kioke.commons.exception;

import kioke.commons.constant.ErrorCode;

public abstract class KiokeException extends Exception {

  protected String detail;

  public KiokeException() {
    this.detail = "";
  }

  public KiokeException(String detail) {
    super(detail);
    this.detail = detail;
  }

  public abstract ErrorCode getErrorCode();

  public String getDetail() {
    return detail;
  }
}
