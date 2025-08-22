package io.kioke.feature.journal.util;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.response.CreateJournalResponseDto;
import io.kioke.feature.journal.dto.response.GetJournalResponseDto;
import io.kioke.feature.page.domain.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JournalMapper {

  @Mapping(source = "pageId", target = "id")
  public JournalDto.Page toDto(Page page);

  @Mapping(source = "journalId", target = "id")
  public JournalDto toDto(Journal journal);

  public CreateJournalResponseDto toCreateJournalResponse(JournalDto journal);

  public GetJournalResponseDto toGetJournalResponse(JournalDto journal);
}
