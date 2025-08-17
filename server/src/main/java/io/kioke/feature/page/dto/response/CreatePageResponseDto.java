package io.kioke.feature.page.dto.response;

import jakarta.validation.constraints.NotNull;

public record CreatePageResponseDto(@NotNull String pageId) {}
