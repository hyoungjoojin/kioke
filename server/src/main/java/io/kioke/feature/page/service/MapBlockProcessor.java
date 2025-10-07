package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.domain.block.MapBlock;
import io.kioke.feature.page.dto.BlockContentDto;
import io.kioke.feature.page.dto.BlockDto;
import io.kioke.feature.page.dto.MapBlockContentDto;
import io.kioke.feature.user.domain.User;
import org.springframework.stereotype.Service;

@Service
class MapBlockProcessor implements BlockProcessor {

  @Override
  public BlockType type() {
    return BlockType.MAP_BLOCK;
  }

  @Override
  public BlockDto map(Block block) {
    MapBlockContentDto content = new MapBlockContentDto(BlockType.MAP_BLOCK);
    return new BlockDto(block.getBlockId(), content);
  }

  @Override
  public Block createBlock(User requester, BlockContentDto content) {
    MapBlock mapBlock = new MapBlock();
    return mapBlock;
  }

  @Override
  public Block updateBlock(User requester, Block block, BlockContentDto content) {
    MapBlock mapBlock = new MapBlock();
    return mapBlock;
  }
}
