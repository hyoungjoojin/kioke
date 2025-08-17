package io.kioke.feature.journal.dto.response;

import jakarta.validation.constraints.NotNull;

public record CreateJournalCollectionResponseDto(@NotNull String collectionId) {}
