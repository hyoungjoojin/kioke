package io.kioke.feature.block.dto;

import io.kioke.feature.block.domain.BlockType;

public record MarkerBlockDto(
    String id,
    BlockType type,
    String parentId,
    double latitude,
    double longitude,
    String title,
    String description)
    implements BlockDto {}
