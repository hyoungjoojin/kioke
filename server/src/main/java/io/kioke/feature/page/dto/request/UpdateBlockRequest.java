package io.kioke.feature.page.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.dto.BlockContent;
import io.kioke.feature.page.dto.ImageBlockContent;
import io.kioke.feature.page.dto.TextBlockContent;

public record UpdateBlockRequest(
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            visible = true,
            include = JsonTypeInfo.As.EXISTING_PROPERTY,
            property = "type")
        @JsonSubTypes({
          @Type(value = TextBlockContent.class, name = BlockType.Values.TEXT_BLOCK),
          @Type(value = ImageBlockContent.class, name = BlockType.Values.IMAGE_BLOCK)
        })
        BlockContent content) {}
