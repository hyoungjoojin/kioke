package io.kioke.feature.block.service.processor;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import io.kioke.feature.block.domain.blocks.GalleryBlock;
import io.kioke.feature.block.dto.BlockDto;
import io.kioke.feature.block.dto.GalleryBlockDto;
import io.kioke.feature.block.dto.operation.UpdateBlockOperation;
import org.springframework.stereotype.Component;

@Component
public class GalleryBlockProcessor implements BlockProcessor {

  @Override
  public BlockType type() {
    return BlockType.GALLERY_BLOCK;
  }

  @Override
  public BlockDto map(Block block) {
    return new GalleryBlockDto(block.getBlockId(), block.getType());
  }

  @Override
  public Block getUpdatedBlock(UpdateBlockOperation operation) {
    return new GalleryBlock();
  }
}
