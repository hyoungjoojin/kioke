package io.kioke.feature.page.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kioke.feature.page.domain.block.BlockType;
import java.util.List;

public record UpdateBlockRequestDto(
    BlockType type,
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            visible = true,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type")
        @JsonSubTypes({
          @Type(value = TextBlockContent.class, name = "TEXT_BLOCK"),
          @Type(value = ImageBlockContent.class, name = "IMAGE_BLOCK")
        })
        BlockContent content) {

  public static interface BlockContent {}

  public static record TextBlockContent(String content) implements BlockContent {}

  public static record ImageBlockContent(List<String> images) implements BlockContent {}
}
