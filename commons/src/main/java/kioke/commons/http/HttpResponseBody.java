package kioke.commons.http;

import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

@Data
@AllArgsConstructor
public class HttpResponseBody<T> {
  private String requestId;
  private boolean success;
  private HttpStatus status;
  private OffsetDateTime timestamp;
  private Optional<T> data;
  private Optional<ProblemDetail> error;

  public static <T> HttpResponseBody<T> success(String requestId, HttpStatus status, T data) {
    return new HttpResponseBody<T>(
        requestId, true, status, OffsetDateTime.now(), Optional.of(data), Optional.empty());
  }

  public static <T> HttpResponseBody<T> error(
      String requestId, HttpStatus status, ProblemDetail error) {
    return new HttpResponseBody<T>(
        requestId, false, status, OffsetDateTime.now(), Optional.empty(), Optional.of(error));
  }
}
