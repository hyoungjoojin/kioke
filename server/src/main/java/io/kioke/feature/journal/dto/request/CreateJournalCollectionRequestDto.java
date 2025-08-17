package io.kioke.feature.journal.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateJournalCollectionRequestDto(@NotNull String name) {}
