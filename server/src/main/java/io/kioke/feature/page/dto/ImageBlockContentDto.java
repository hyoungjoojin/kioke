package io.kioke.feature.page.dto;

import io.kioke.feature.page.domain.block.BlockType;
import java.util.List;

public record ImageBlockContentDto(BlockType type, List<Image> images) implements BlockContentDto {

  public static record Image(
      String imageId, String imageUrl, String description, Long width, Long height) {}
}
