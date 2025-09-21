package io.kioke.feature.journal.dto.response;

import io.kioke.feature.journal.domain.JournalRole;
import jakarta.validation.constraints.NotNull;

public record CreateJournalResponse(@NotNull String journalId, @NotNull Creator creator) {

  public static record Creator(@NotNull String userId, @NotNull JournalRole role) {}
}
