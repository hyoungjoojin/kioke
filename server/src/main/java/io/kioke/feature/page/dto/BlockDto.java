package io.kioke.feature.page.dto;

public record BlockDto(String blockId, BlockContentDto content) {

  public static BlockDto of(String blockId) {
    return new BlockDto(blockId, null);
  }
}
