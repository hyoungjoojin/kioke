package io.kioke.feature.block.dto;

import io.kioke.feature.block.domain.BlockType;

public interface BlockDto {

  public String id();

  public BlockType type();
}
