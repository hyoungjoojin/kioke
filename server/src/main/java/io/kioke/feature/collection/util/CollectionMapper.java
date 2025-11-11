package io.kioke.feature.collection.util;

import io.kioke.feature.collection.domain.Collection;
import io.kioke.feature.collection.domain.CollectionEntry;
import io.kioke.feature.collection.dto.CollectionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CollectionMapper {

  public CollectionDto toDto(Collection collection);

  @Mappings({
    @Mapping(source = "entry.journal.id", target = "id"),
    @Mapping(source = "entry.journal.title", target = "title"),
  })
  CollectionDto.Journal toDto(CollectionEntry entry);
}
