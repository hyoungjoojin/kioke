package io.kioke.feature.block.service.processor;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import io.kioke.feature.block.domain.blocks.MapBlock;
import io.kioke.feature.block.domain.blocks.MarkerBlock;
import io.kioke.feature.block.dto.BlockDto;
import io.kioke.feature.block.dto.MarkerBlockDto;
import io.kioke.feature.block.dto.operation.UpdateBlockOperation;
import io.kioke.feature.block.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarkerBlockProcessor implements BlockProcessor {

  private final BlockRepository blockRepository;

  @Override
  public BlockType type() {
    return BlockType.MARKER_BLOCK;
  }

  @Override
  public BlockDto map(Block block) {
    MarkerBlock markerBlock = (MarkerBlock) block;

    return new MarkerBlockDto(
        markerBlock.getBlockId(),
        type(),
        markerBlock.getParent().getBlockId(),
        markerBlock.getLatitude(),
        markerBlock.getLongitude(),
        markerBlock.getTitle(),
        markerBlock.getDescription());
  }

  @Override
  public Block getUpdatedBlock(UpdateBlockOperation operation) {
    MarkerBlock markerBlock = new MarkerBlock();
    var content = (UpdateBlockOperation.MarkerBlockContent) operation.content();

    MapBlock parentBlock = (MapBlock) blockRepository.findById(content.parentId()).orElseThrow();
    markerBlock.setParent(parentBlock);

    markerBlock.setLatitude(content.latitude().longValue());
    markerBlock.setLongitude(content.longitude().longValue());
    markerBlock.setTitle(content.title());
    markerBlock.setDescription(content.description());

    return markerBlock;
  }
}
