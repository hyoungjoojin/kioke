package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.domain.block.TextBlock;
import io.kioke.feature.page.dto.BlockContentDto;
import io.kioke.feature.page.dto.BlockDto;
import io.kioke.feature.page.dto.TextBlockContentDto;
import io.kioke.feature.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class TextBlockProcessor implements BlockProcessor {

  @Override
  public BlockType type() {
    return BlockType.TEXT_BLOCK;
  }

  @Override
  public BlockDto map(Block block) {
    TextBlock textBlock = (TextBlock) block;
    TextBlockContentDto content =
        new TextBlockContentDto(BlockType.TEXT_BLOCK, textBlock.getText());
    return new BlockDto(block.getBlockId(), content);
  }

  @Override
  public Block createBlock(User requester, BlockContentDto content) {
    TextBlockContentDto textBlockContent = (TextBlockContentDto) content;

    TextBlock block = new TextBlock();
    block.setText(textBlockContent.text());
    return block;
  }

  @Override
  public Block updateBlock(User requester, Block block, BlockContentDto content) {
    TextBlock textBlock = (TextBlock) block;
    TextBlockContentDto textBlockContent = (TextBlockContentDto) content;

    textBlock.setText(textBlockContent.text());
    return textBlock;
  }
}
