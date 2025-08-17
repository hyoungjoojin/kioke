package io.kioke.feature.journal.dto;

import java.util.List;

public record JournalCollectionDto(
    String collectionId, String name, List<Journal> journals, boolean isDefault) {

  public static record Journal(String journalId, String title) {}
}
