package io.kioke.feature.page.dto.request;

import java.time.LocalDateTime;

public record UpdatePageRequestDto(String title, String content, LocalDateTime date) {

  public static UpdatePageRequestDto emptyRequest() {
    return new UpdatePageRequestDto(null, null, null);
  }
}
