package io.kioke.feature.page.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreatePageRequestDto(
    @NotNull String journalId, @NotNull String title, @NotNull LocalDateTime date) {}
