package io.kioke.feature.page.util;

import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.response.CreatePageResponseDto;
import io.kioke.feature.page.dto.response.GetPageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PageMapper {

  @Mapping(source = "page.journal.journalId", target = "journalId")
  public PageDto toDto(Page page);

  public GetPageResponseDto toGetPageResponse(PageDto page);

  public CreatePageResponseDto toCreatePageResponse(PageDto page);
}
