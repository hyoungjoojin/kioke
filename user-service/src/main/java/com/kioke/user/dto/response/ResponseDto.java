package com.kioke.user.dto.response;

import com.kioke.user.dto.response.data.ResponseDataDto;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ProblemDetail;

@Data
@Builder
public class ResponseDto<T extends ResponseDataDto> {
  private final String requestId;
  private final String path;
  private final OffsetDateTime timestamp;
  private final Integer status;
  private final boolean success;
  private final Optional<T> data;
  private final Optional<ProblemDetail> error;
}
