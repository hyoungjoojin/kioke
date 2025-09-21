package io.kioke.feature.page.dto.response;

import io.kioke.feature.page.domain.block.BlockType;
import java.time.LocalDateTime;
import java.util.List;

public record PageResponse(
    String pageId, String journalId, String title, List<Block> blocks, LocalDateTime date) {

  public static record Block(String id, BlockType type, BlockContent content) {}

  public static interface BlockContent {}

  public static record TextBlockContent(String text) implements BlockContent {}

  public static record ImageBlockContent(List<String> images) implements BlockContent {}
}
