package io.kioke.feature.block.service.processor;

import io.kioke.feature.block.domain.Block;
import io.kioke.feature.block.domain.BlockType;
import io.kioke.feature.block.domain.blocks.TextBlock;
import io.kioke.feature.block.dto.BlockDto;
import io.kioke.feature.block.dto.TextBlockDto;
import io.kioke.feature.block.dto.operation.UpdateBlockOperation;
import org.springframework.stereotype.Component;

@Component
class TextBlockProcessor implements BlockProcessor {

  @Override
  public BlockType type() {
    return BlockType.TEXT_BLOCK;
  }

  @Override
  public BlockDto map(Block block) {
    TextBlock textBlock = (TextBlock) block;
    return new TextBlockDto(textBlock.getBlockId(), textBlock.getType(), textBlock.getText());
  }

  @Override
  public Block getUpdatedBlock(UpdateBlockOperation operation) {
    TextBlock textBlock = new TextBlock();

    var content = (UpdateBlockOperation.TextBlockContent) operation.content();
    textBlock.setText(content.text());

    return textBlock;
  }
}
