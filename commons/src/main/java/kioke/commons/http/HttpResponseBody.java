package kioke.commons.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import kioke.commons.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
public class HttpResponseBody<T> {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String requestId;

  private boolean success;
  private String path;
  private HttpStatusCode status;
  private OffsetDateTime timestamp;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ErrorDetail error;

  public static <T> HttpResponseBody<T> success(
      HttpServletRequest request, HttpStatusCode status, T data) {
    return new HttpResponseBody<T>(
        (String) request.getAttribute("requestId"),
        true,
        request.getRequestURI(),
        status,
        OffsetDateTime.now(),
        data,
        null);
  }

  public static <T> HttpResponseBody<T> error(HttpServletRequest request, ErrorCode errorCode) {
    return new HttpResponseBody<T>(
        (String) request.getAttribute("requestId"),
        false,
        request.getRequestURI(),
        errorCode.getStatus(),
        OffsetDateTime.now(),
        null,
        new ErrorDetail(errorCode));
  }

  public static <T> HttpResponseBody<T> error(
      HttpServletRequest request, ErrorCode errorCode, String details) {
    return new HttpResponseBody<T>(
        (String) request.getAttribute("requestId"),
        false,
        request.getRequestURI(),
        errorCode.getStatus(),
        OffsetDateTime.now(),
        null,
        new ErrorDetail(errorCode, details));
  }
}
