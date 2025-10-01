package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.domain.block.TextBlock;
import io.kioke.feature.page.dto.BlockContent;
import io.kioke.feature.page.dto.TextBlockContent;
import io.kioke.feature.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public class TextBlockProcessor implements BlockProcessor {

  @Override
  public BlockType type() {
    return BlockType.TEXT_BLOCK;
  }

  @Override
  public TextBlock createBlock(User requester, BlockContent content) {
    TextBlockContent textBlockContent = (TextBlockContent) content;

    TextBlock block = new TextBlock();
    block.setText(textBlockContent.text());
    return block;
  }

  @Override
  public Block updateBlock(User requester, Block block, BlockContent content) {
    TextBlock textBlock = (TextBlock) block;
    TextBlockContent textBlockContent = (TextBlockContent) content;

    textBlock.setText(textBlockContent.text());
    return textBlock;
  }
}
