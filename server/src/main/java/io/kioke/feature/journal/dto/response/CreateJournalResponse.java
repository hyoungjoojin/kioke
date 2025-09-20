package io.kioke.feature.journal.dto.response;

import jakarta.validation.constraints.NotNull;

public record CreateJournalResponse(@NotNull String journalId) {

  public static CreateJournalResponse of(String journalId) {
    return new CreateJournalResponse(journalId);
  }
}
