package io.kioke.feature.page.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record UpdatePageRequestDto(
    @NotNull String journalId, String title, String content, LocalDateTime date) {}
