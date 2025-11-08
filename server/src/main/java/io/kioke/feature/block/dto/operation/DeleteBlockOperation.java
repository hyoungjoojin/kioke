package io.kioke.feature.block.dto.operation;

import jakarta.validation.constraints.NotNull;

public record DeleteBlockOperation(@NotNull String blockId, @NotNull String pageId)
    implements BlockOperation {}
