package io.kioke.feature.page.util;

import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.domain.block.ImageBlock;
import io.kioke.feature.page.domain.block.ImageBlockImage;
import io.kioke.feature.page.domain.block.MapBlock;
import io.kioke.feature.page.domain.block.TextBlock;
import io.kioke.feature.page.dto.BlockContent;
import io.kioke.feature.page.dto.ImageBlockContent;
import io.kioke.feature.page.dto.MapBlockContent;
import io.kioke.feature.page.dto.TextBlockContent;
import io.kioke.feature.page.dto.response.CreateBlockResponse;
import io.kioke.feature.page.dto.response.CreatePageResponse;
import io.kioke.feature.page.dto.response.PageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PageMapper {

  @Mappings({@Mapping(source = "page.journal.journalId", target = "journalId")})
  public PageResponse mapToPageResponse(Page page);

  public CreatePageResponse mapToCreatePageResponse(Page page);

  public CreateBlockResponse mapToCreateBlockResponse(Block block);

  default PageResponse.Block map(Block block) {
    if (block == null) {
      return null;
    }

    BlockContent content;
    if (block instanceof TextBlock textBlock) {
      content = map(textBlock);
    } else if (block instanceof ImageBlock imageBlock) {
      content = map(imageBlock);
    } else if (block instanceof MapBlock mapBlock) {
      content = map(mapBlock);
    } else {
      throw new IllegalArgumentException("Unknown block type " + block.getClass());
    }

    return new PageResponse.Block(block.getBlockId(), content);
  }

  @Mapping(target = "type", constant = BlockType.Values.TEXT_BLOCK)
  TextBlockContent map(TextBlock block);

  @Mapping(target = "type", constant = BlockType.Values.IMAGE_BLOCK)
  ImageBlockContent map(ImageBlock block);

  @Mapping(source = "image.id", target = "imageId")
  ImageBlockContent.Image map(ImageBlockImage image);

  @Mapping(target = "type", constant = BlockType.Values.MAP_BLOCK)
  MapBlockContent map(MapBlock block);
}
