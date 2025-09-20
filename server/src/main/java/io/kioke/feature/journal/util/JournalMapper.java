package io.kioke.feature.journal.util;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalUser;
import io.kioke.feature.journal.dto.response.CreateJournalResponse;
import io.kioke.feature.journal.dto.response.JournalResponse;
import io.kioke.feature.page.domain.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface JournalMapper {

  @Mappings({@Mapping(source = "journal.journalId", target = "id")})
  public JournalResponse mapToJournalResponse(Journal journal);

  @Mapping(source = "pageId", target = "id")
  JournalResponse.Page map(Page page);

  @Mapping(source = "user.userId", target = "userId")
  JournalResponse.User map(JournalUser user);

  CreateJournalResponse mapToCreateJournalResponse(Journal journal);
}
