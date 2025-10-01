package io.kioke.feature.page.service;

import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.dto.BlockContent;
import io.kioke.feature.user.domain.User;

public interface BlockProcessor {

  public BlockType type();

  public Block createBlock(User requester, BlockContent content);

  public Block updateBlock(User requester, Block block, BlockContent content);
}
