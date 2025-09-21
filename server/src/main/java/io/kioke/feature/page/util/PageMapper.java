package io.kioke.feature.page.util;

import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.domain.block.BlockType;
import io.kioke.feature.page.domain.block.TextBlock;
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

    BlockType type;
    PageResponse.BlockContent content;

    if (block instanceof TextBlock textBlock) {
      type = BlockType.TEXT_BLOCK;
      content = map(textBlock);
    } else {
      throw new IllegalArgumentException("Unknown block type " + block.getClass());
    }

    return new PageResponse.Block(block.getBlockId(), type, content);
  }

  PageResponse.TextBlockContent map(TextBlock block);
}
