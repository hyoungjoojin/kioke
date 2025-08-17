package io.kioke.feature.journal.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record JournalDto(
    String journalId, String title, String description, boolean isPublic, List<Page> pages) {

  public JournalDto(String journalId) {
    this(journalId, null, null, false, new ArrayList<>());
  }

  public static record Page(String pageId, String title, LocalDateTime date) {}
}
