package io.kioke.feature.page.dto.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.dto.BlockContentDto;
import io.kioke.feature.page.dto.ImageBlockContentDto;
import io.kioke.feature.page.dto.MapBlockContentDto;
import io.kioke.feature.page.dto.TextBlockContentDto;
import jakarta.validation.constraints.NotNull;

public record CreateBlockRequest(
    @NotNull String pageId,
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            visible = true,
            include = JsonTypeInfo.As.EXISTING_PROPERTY,
            property = "type")
        @JsonSubTypes({
          @Type(value = TextBlockContentDto.class, name = BlockType.Values.TEXT_BLOCK),
          @Type(value = ImageBlockContentDto.class, name = BlockType.Values.IMAGE_BLOCK),
          @Type(value = MapBlockContentDto.class, name = BlockType.Values.MAP_BLOCK)
        })
        BlockContentDto content) {}
