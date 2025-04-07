package kioke.commons.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import kioke.commons.constant.ErrorCode;
import lombok.Data;

@Data
public class ErrorDetail {

  private String code;
  private String title;
  private String message;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private String details;

  public ErrorDetail(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.title = errorCode.getTitle();
    this.message = errorCode.getMessage();
    this.details = null;
  }

  public ErrorDetail(ErrorCode errorCode, String details) {
    this.code = errorCode.getCode();
    this.title = errorCode.getTitle();
    this.message = errorCode.getMessage();
    this.details = details;
  }
}
