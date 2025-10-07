package io.kioke.feature.page.dto;

import io.kioke.feature.page.domain.block.BlockType;

public record MapBlockContentDto(BlockType type) implements BlockContentDto {}
