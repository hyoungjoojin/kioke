package io.kioke.feature.block.dto;

import io.kioke.feature.block.domain.BlockType;

public record TextBlockDto(String id, BlockType type, String text) implements BlockDto {}
