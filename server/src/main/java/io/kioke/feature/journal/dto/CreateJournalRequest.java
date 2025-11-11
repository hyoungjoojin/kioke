package io.kioke.feature.journal.dto;

import jakarta.validation.constraints.NotNull;

public record CreateJournalRequest(@NotNull String collectionId, @NotNull String title) {}
