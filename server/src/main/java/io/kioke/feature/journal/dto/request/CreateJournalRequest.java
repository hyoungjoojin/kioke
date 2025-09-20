package io.kioke.feature.journal.dto.request;

import io.kioke.feature.journal.domain.JournalType;
import jakarta.validation.constraints.NotNull;

public record CreateJournalRequest(@NotNull String title, @NotNull JournalType type) {}
