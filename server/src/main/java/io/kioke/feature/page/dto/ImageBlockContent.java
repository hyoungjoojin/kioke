package io.kioke.feature.page.dto;

import io.kioke.feature.page.domain.block.BlockType;
import java.util.List;

public record ImageBlockContent(BlockType type, List<Image> images) implements BlockContent {

  public static record Image(String imageId, String description) {}
}
