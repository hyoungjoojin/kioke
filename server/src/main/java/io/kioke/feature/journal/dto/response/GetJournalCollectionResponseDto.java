package io.kioke.feature.journal.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record GetJournalCollectionResponseDto(
    @NotNull String collectionId,
    @NotNull String name,
    @NotNull List<Journal> journals,
    boolean isDefault) {

  public static record Journal(@NotNull String journalId, String title) {}
}
