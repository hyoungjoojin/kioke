package kioke.commons.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import kioke.commons.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class HttpResponseBody<T> {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String requestId;

  private boolean success;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String path;

  private HttpStatus status;
  private OffsetDateTime timestamp;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ErrorDetail error;

  public static <T> HttpResponseBody<T> success(
      HttpServletRequest request, HttpStatus status, T data) {

    return HttpResponseBody.<T>builder()
        .requestId(request != null ? (String) request.getAttribute("requestId") : null)
        .success(true)
        .path(request != null ? request.getRequestURI() : null)
        .status(status)
        .timestamp(OffsetDateTime.now())
        .data(data)
        .error(null)
        .build();
  }

  public static <T> HttpResponseBody<T> error(HttpServletRequest request, ErrorCode errorCode) {

    return HttpResponseBody.<T>builder()
        .requestId(request != null ? (String) request.getAttribute("requestId") : null)
        .success(false)
        .path(request != null ? request.getRequestURI() : null)
        .status(errorCode.getStatus())
        .timestamp(OffsetDateTime.now())
        .data(null)
        .error(new ErrorDetail(errorCode))
        .build();
  }

  public static <T> HttpResponseBody<T> error(
      HttpServletRequest request, ErrorCode errorCode, String details) {

    return HttpResponseBody.<T>builder()
        .requestId(request != null ? (String) request.getAttribute("requestId") : null)
        .success(false)
        .path(request != null ? request.getRequestURI() : null)
        .status(errorCode.getStatus())
        .timestamp(OffsetDateTime.now())
        .data(null)
        .error(new ErrorDetail(errorCode, details))
        .build();
  }
}
