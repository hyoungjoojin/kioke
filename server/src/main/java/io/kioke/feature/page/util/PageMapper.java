package io.kioke.feature.page.util;

import io.kioke.feature.block.dto.BlockDto;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.dto.PageDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PageMapper {

  @Mappings({
    @Mapping(source = "page.journal.journalId", target = "journalId"),
    @Mapping(source = "blocks", target = "blocks")
  })
  public PageDto map(Page page, List<BlockDto> blocks);
}
