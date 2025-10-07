package io.kioke.feature.page.dto;

import io.kioke.feature.page.domain.block.BlockType;

public record TextBlockContentDto(BlockType type, String text) implements BlockContentDto {}
