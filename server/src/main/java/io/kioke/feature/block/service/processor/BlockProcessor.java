package io.kioke.feature.block.service.processor;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import io.kioke.feature.block.dto.BlockDto;
import io.kioke.feature.block.dto.operation.UpdateBlockOperation;

public interface BlockProcessor {

  public BlockType type();

  public BlockDto map(Block block);

  public Block getUpdatedBlock(UpdateBlockOperation operation);
}
