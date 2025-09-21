package io.kioke.feature.page.dto.response;

import jakarta.validation.constraints.NotNull;

public record CreatePageResponse(@NotNull String pageId) {}
