package io.kioke.feature.block.dto;

import io.kioke.feature.block.domain.BlockType;

public record GalleryBlockDto(String id, BlockType type) implements BlockDto {}
