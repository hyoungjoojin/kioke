package com.kioke.journal.dto.response;

import com.kioke.journal.dto.response.data.ResponseDataDto;
import com.kioke.journal.dto.response.error.ResponseErrorDto;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto<T extends ResponseDataDto> {
  private final String requestId;
  private final String path;
  private final OffsetDateTime timestamp;
  private final Integer status;
  private final boolean success;
  private final Optional<T> data;
  private final Optional<ResponseErrorDto> error;
}
