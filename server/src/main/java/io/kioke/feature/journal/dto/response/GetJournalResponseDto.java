package io.kioke.feature.journal.dto.response;

import java.util.List;

public record GetJournalResponseDto(String id, String title, String description, List<Page> pages) {

  public static record Page(String id, String title) {}
}
