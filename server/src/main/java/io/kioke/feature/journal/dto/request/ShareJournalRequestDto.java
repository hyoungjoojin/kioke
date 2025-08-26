package io.kioke.feature.journal.dto.request;

import io.kioke.feature.journal.constant.Role;
import jakarta.validation.constraints.NotNull;

public record ShareJournalRequestDto(@NotNull String userId, @NotNull Role role) {}
