package io.kioke.feature.journal.dto.request;

import io.kioke.feature.journal.constant.JournalType;
import jakarta.validation.constraints.NotNull;

public record CreateJournalRequestDto(
    @NotNull String collectionId, @NotNull JournalType type, @NotNull String title) {}
