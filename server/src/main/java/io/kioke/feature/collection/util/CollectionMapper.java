package io.kioke.feature.collection.util;

import io.kioke.feature.collection.domain.Collection;
import io.kioke.feature.collection.domain.CollectionEntry;
import io.kioke.feature.collection.dto.CollectionDto;
import io.kioke.feature.collection.dto.response.CreateCollectionResponseDto;
import io.kioke.feature.collection.dto.response.GetCollectionResponseDto;
import io.kioke.feature.collection.dto.response.GetCollectionsResponseDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CollectionMapper {

  @Mappings({
    @Mapping(source = "collectionEntry.journal.journalId", target = "id"),
    @Mapping(source = "collectionEntry.journal.title", target = "title")
  })
  CollectionDto.Journal toDto(CollectionEntry collectionEntry);

  @Mappings({
    @Mapping(source = "collectionId", target = "id"),
    @Mapping(source = "entries", target = "journals")
  })
  public CollectionDto toDto(Collection collection);

  public CreateCollectionResponseDto toCreateCollectionResponse(CollectionDto collection);

  public GetCollectionResponseDto toGetCollectionResponse(CollectionDto collection);

  public GetCollectionsResponseDto toGetCollectionsResponse(
      int count, List<CollectionDto> collections);
}
