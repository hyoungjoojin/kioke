package io.kioke.feature.journal.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetJournalResponseDto(
    String journalId, String title, String description, List<Page> pages) {

  public static record Page(String pageId, String title, LocalDateTime date) {}
}
