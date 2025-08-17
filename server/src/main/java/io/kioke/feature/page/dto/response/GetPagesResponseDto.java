package io.kioke.feature.page.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetPagesResponseDto(String journalId, List<Page> pages) {

  public static record Page(String pageId, String title, LocalDateTime date) {}
}
