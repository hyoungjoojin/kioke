package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.dto.BlockContentDto;
import io.kioke.feature.page.dto.BlockDto;
import io.kioke.feature.user.domain.User;

public interface BlockProcessor {

  public BlockType type();

  public BlockDto map(Block block);

  public Block createBlock(User requester, BlockContentDto content);

  public Block updateBlock(User requester, Block block, BlockContentDto content);
}
