package io.kioke.feature.page.dto;

import io.kioke.feature.page.domain.block.BlockType;

public record TextBlockContent(BlockType type, String text) implements BlockContent {}
