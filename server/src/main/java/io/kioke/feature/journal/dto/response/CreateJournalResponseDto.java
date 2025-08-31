package io.kioke.feature.journal.dto.response;

import io.kioke.feature.journal.constant.JournalType;
import jakarta.validation.constraints.NotNull;

public record CreateJournalResponseDto(@NotNull String id, JournalType type) {}
