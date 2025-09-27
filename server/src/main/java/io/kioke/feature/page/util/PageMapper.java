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

    if (block instanceof TextBlock textBlock) {
      return map(textBlock);
    } else {
      throw new IllegalArgumentException("Unknown block type " + block.getClass());
    }
  }

  @Mapping(target = "type", constant = BlockType.Values.TEXT_BLOCK)
  PageResponse.TextBlock map(TextBlock block);
}
