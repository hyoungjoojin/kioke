package io.kioke.feature.page.dto.response;

import io.kioke.feature.page.domain.block.BlockType;
import java.time.LocalDateTime;
import java.util.List;

public record PageResponse(
    String pageId, String journalId, String title, List<Block> blocks, LocalDateTime date) {

  public static interface Block {

    public String blockId();

    public BlockType type();
  }

  public static record TextBlock(String blockId, BlockType type, String text) implements Block {}
}
