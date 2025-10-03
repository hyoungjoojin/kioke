package io.kioke.feature.page.dto;

import io.kioke.feature.page.domain.block.BlockType;

public record MapBlockContent(BlockType type) implements BlockContent {}
