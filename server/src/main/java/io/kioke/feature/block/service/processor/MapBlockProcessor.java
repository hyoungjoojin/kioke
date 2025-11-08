package io.kioke.feature.block.service.processor;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import io.kioke.feature.block.domain.blocks.MapBlock;
import io.kioke.feature.block.dto.BlockDto;
import io.kioke.feature.block.dto.MapBlockDto;
import io.kioke.feature.block.dto.operation.UpdateBlockOperation;
import org.springframework.stereotype.Component;

@Component
public class MapBlockProcessor implements BlockProcessor {

  @Override
  public BlockType type() {
    return BlockType.MAP_BLOCK;
  }

  @Override
  public BlockDto map(Block block) {
    return new MapBlockDto(block.getBlockId(), type());
  }

  @Override
  public Block getUpdatedBlock(UpdateBlockOperation operation) {
    return new MapBlock();
  }
}
