package io.kioke.feature.collection.service;

import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.feature.collection.domain.Collection;
import io.kioke.feature.collection.dto.CollectionDto;
import io.kioke.feature.collection.dto.request.CreateCollectionRequestDto;
import io.kioke.feature.collection.repository.CollectionRepository;
import io.kioke.feature.collection.util.CollectionMapper;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionService {

  private final CollectionRepository collectionRepository;
  private final CollectionMapper collectionMapper;

  public CollectionService(
      CollectionRepository collectionRepository, CollectionMapper collectionMapper) {
    this.collectionRepository = collectionRepository;
    this.collectionMapper = collectionMapper;
  }

  @Transactional
  public CollectionDto createCollection(UserDto userDto, CreateCollectionRequestDto request) {
    User user = User.builder().userId(userDto.userId()).build();

    Collection collection = Collection.builder().user(user).name(request.name()).build();
    collection = collectionRepository.save(collection);
    return collectionMapper.toDto(collection);
  }

  @Transactional(readOnly = true)
  public List<CollectionDto> getCollections(UserDto userDto) {
    User user = User.builder().userId(userDto.userId()).build();
    return collectionRepository.findByUser(user).stream().map(collectionMapper::toDto).toList();
  }

  @Transactional(readOnly = true)
  public CollectionDto getCollection(UserDto userDto, String collectionId)
      throws CollectionNotFoundException {
    User user = User.builder().userId(userDto.userId()).build();

    Collection collection =
        collectionRepository
            .findByUserAndId(user, collectionId)
            .orElseThrow(() -> new CollectionNotFoundException());

    return collectionMapper.toDto(collection);
  }

  @Transactional
  public void addEntryToCollection(User user, Journal journal, String collectionId)
      throws CollectionNotFoundException {
    Collection collection =
        collectionRepository
            .findByUserAndId(user, collectionId)
            .orElseThrow(() -> new CollectionNotFoundException());
    collection.addJournal(journal);
    collectionRepository.save(collection);
  }
}
