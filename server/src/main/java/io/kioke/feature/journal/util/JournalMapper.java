package io.kioke.feature.journal.util;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.page.domain.Page;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JournalMapper {

  public JournalDto toDto(Journal journal, List<Page> pages, String cover);

  @Mapping(source = "pageId", target = "id")
  JournalDto.Page toDto(Page page);
}
