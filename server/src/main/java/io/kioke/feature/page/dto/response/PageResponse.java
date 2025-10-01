package io.kioke.feature.page.dto.response;

import io.kioke.feature.page.dto.BlockContent;
import java.time.LocalDateTime;
import java.util.List;

public record PageResponse(
    String pageId, String journalId, String title, List<Block> blocks, LocalDateTime date) {

  public static record Block(String blockId, BlockContent content) {}

  //
  // public static record TextBlock(String blockId, BlockType type, String text) implements Block {}
  //
  // public static record ImageBlock(String blockId, BlockType type, List<Image> imageIds)
  //     implements Block {}
}
