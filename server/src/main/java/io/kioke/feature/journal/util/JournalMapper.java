package io.kioke.feature.journal.util;

import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalCollection;
import io.kioke.feature.journal.dto.JournalCollectionDto;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.response.CreateJournalResponseDto;
import io.kioke.feature.journal.dto.response.GetJournalCollectionResponseDto;
import io.kioke.feature.journal.dto.response.GetJournalCollectionsResponseDto;
import io.kioke.feature.journal.dto.response.GetJournalResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface JournalMapper {

  public JournalDto toJournalDto(Journal journal, List<JournalDto.Page> pages);

  @Mappings({@Mapping(source = "collection.isDefaultCollection", target = "isDefault")})
  public JournalCollectionDto toJournalCollectionDto(
      JournalCollection collection, List<Journal> journals);

  public GetJournalResponseDto toGetJournalResponse(JournalDto journal);

  public CreateJournalResponseDto toCreateJournalResponse(JournalDto journal);

  public GetJournalCollectionResponseDto toGetJournalCollectionResponse(
      JournalCollectionDto collection);

  GetJournalCollectionsResponseDto toGetJournalCollectionsResponse(
      int x, List<JournalCollectionDto> collections);

  public default GetJournalCollectionsResponseDto toGetJournalCollectionsResponse(
      List<JournalCollectionDto> collections) {
    return toGetJournalCollectionsResponse(0, collections);
  }
}
