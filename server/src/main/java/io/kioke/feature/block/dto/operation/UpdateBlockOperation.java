package io.kioke.feature.block.dto.operation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kioke.feature.block.domain.BlockType;
import jakarta.validation.constraints.NotNull;

public record UpdateBlockOperation(
    String blockId,
    String pageId,
    BlockType type,
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            visible = true,
            include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
            property = "type")
        @JsonSubTypes({
          @Type(value = TextBlockContent.class, name = BlockType.Values.TEXT_BLOCK),
          @Type(value = ImageBlockContent.class, name = BlockType.Values.IMAGE_BLOCK),
          @Type(value = GalleryBlockContent.class, name = BlockType.Values.GALLERY_BLOCK)
        })
        Content content)
    implements BlockOperation {

  public interface Content {}

  public record TextBlockContent(@NotNull String text) implements Content {}

  public record ImageBlockContent(@NotNull String parentId, @NotNull String imageId)
      implements Content {}

  public record GalleryBlockContent() implements Content {}
}
