package io.kioke.feature.journal.dto;

import java.util.List;

public record JournalDto(
    String id, String title, String description, boolean isPublic, List<Page> pages) {

  public static record Page(String id, String title) {}
}
