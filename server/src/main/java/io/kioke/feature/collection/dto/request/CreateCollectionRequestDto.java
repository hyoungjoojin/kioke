package io.kioke.feature.collection.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateCollectionRequestDto(@NotNull String name) {}
