package io.kioke.feature.page.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kioke.feature.page.domain.block.BlockType;
import java.util.List;

public record UpdateBlockRequest(
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            visible = true,
            include = JsonTypeInfo.As.EXISTING_PROPERTY,
            property = "type")
        @JsonSubTypes({
          @Type(value = TextBlock.class, name = "TEXT_BLOCK"),
          @Type(value = ImageBlock.class, name = "IMAGE_BLOCK")
        })
        BlockContent content) {

  public static interface BlockContent {

    public BlockType type();
  }

  public static record TextBlock(BlockType type, String text) implements BlockContent {}

  public static record ImageBlock(BlockType type, List<String> images) implements BlockContent {}
}
