package io.kioke.feature.journal.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateJournalRequestDto(@NotNull String collectionId, @NotNull String title) {}
