package io.kioke.feature.block.dto;

import io.kioke.feature.block.domain.BlockType;

public record ImageBlockDto(
    String id,
    BlockType type,
    String parentId,
    String url,
    String description,
    long width,
    long height)
    implements BlockDto {}
