package com.kioke.journal.dto.response.error;

import com.kioke.journal.constant.ErrorCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseErrorDto {
  private final String type;
  private final String title;
  private final String detail;
  private final String instance;

  public static ResponseErrorDto from(ErrorCode errorCode, String detail, String instance) {
    return ResponseErrorDto.builder()
        .type(errorCode.getType())
        .title(errorCode.getTitle())
        .detail(detail)
        .instance(instance)
        .build();
  }
}
