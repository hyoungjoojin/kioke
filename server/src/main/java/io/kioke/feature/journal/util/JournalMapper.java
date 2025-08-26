package io.kioke.feature.journal.util;

import io.kioke.feature.journal.constant.Role;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.ShareRequest;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.JournalRoleDto;
import io.kioke.feature.journal.dto.ShareRequestDto;
import io.kioke.feature.journal.dto.response.CreateJournalResponseDto;
import io.kioke.feature.journal.dto.response.GetJournalResponseDto;
import io.kioke.feature.page.domain.Page;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JournalMapper {

  @Mapping(source = "pageId", target = "id")
  public JournalDto.Page toDto(Page page);

  @Mapping(source = "journal.journalId", target = "id")
  public JournalDto toDto(Journal journal, Role role, List<JournalRoleDto> collaborators);

  @Mapping(source = "id", target = "requestId")
  public ShareRequestDto toDto(ShareRequest request);

  public CreateJournalResponseDto toCreateJournalResponse(JournalDto journal);

  public GetJournalResponseDto toGetJournalResponse(JournalDto journal);
}
