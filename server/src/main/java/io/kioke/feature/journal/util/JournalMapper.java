package io.kioke.feature.journal.util;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.JournalDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JournalMapper {

  public JournalDto toDto(Journal journal);
}
