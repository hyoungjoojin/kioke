package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.domain.block.MapBlock;
import io.kioke.feature.page.dto.BlockContent;
import io.kioke.feature.page.dto.MapBlockContent;
import io.kioke.feature.user.domain.User;
import org.springframework.stereotype.Service;

@Service
class MapBlockProcessor implements BlockProcessor {

  @Override
  public BlockType type() {
    return BlockType.MAP_BLOCK;
  }

  @Override
  public Block createBlock(User requester, BlockContent content) {
    MapBlock mapBlock = new MapBlock();
    MapBlockContent mapBlockContent = (MapBlockContent) content;

    return mapBlock;
  }

  @Override
  public Block updateBlock(User requester, Block block, BlockContent content) {
    MapBlock mapBlock = (MapBlock) block;
    MapBlockContent mapBlockContent = (MapBlockContent) content;

    return mapBlock;
  }
}
