package io.kioke.feature.page.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kioke.feature.page.domain.block.BlockType;
import java.util.List;

public record UpdateBlockRequest(
    BlockType type,
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            visible = true,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type")
        @JsonSubTypes({
          @Type(value = UpdateBlockRequest.TextBlock.class, name = BlockType.Values.TEXT_BLOCK)
        })
        BlockContent content) {

  public static interface BlockContent {}

  public static record TextBlock(String text) implements BlockContent {}

  public static record ImageBlock(List<String> images) implements BlockContent {}
}
