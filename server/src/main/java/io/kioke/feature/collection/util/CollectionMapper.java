package io.kioke.feature.collection.util;

import io.kioke.feature.collection.domain.Collection;
import io.kioke.feature.collection.domain.CollectionEntry;
import io.kioke.feature.collection.dto.CollectionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CollectionMapper {

  public CollectionDto toDto(Collection collection);

  CollectionDto.Journal toDto(CollectionEntry collectionEntry);
}
