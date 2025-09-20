package io.kioke.feature.journal.dto.request;

import io.kioke.feature.journal.domain.JournalRole;
import jakarta.validation.constraints.NotNull;

public record ShareJournalRequest(@NotNull String userId, @NotNull JournalRole role) {}
