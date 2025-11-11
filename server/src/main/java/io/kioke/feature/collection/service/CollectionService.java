package io.kioke.feature.collection.service;

import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.feature.collection.domain.Collection;
import io.kioke.feature.collection.domain.CollectionEntry;
import io.kioke.feature.collection.dto.CollectionDto;
import io.kioke.feature.collection.dto.CreateCollectionRequest;
import io.kioke.feature.collection.repository.CollectionRepository;
import io.kioke.feature.collection.util.CollectionMapper;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserPrincipal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CollectionService {

  private final CollectionRepository collectionRepository;
  private final CollectionMapper collectionMapper;

  @Transactional(readOnly = true)
  public CollectionDto getCollectionById(String collectionId) throws CollectionNotFoundException {
    return collectionRepository
        .findById(collectionId)
        .map(collectionMapper::toDto)
        .orElseThrow(CollectionNotFoundException::new);
  }

  @Transactional(readOnly = true)
  public List<CollectionDto> getCollections(UserPrincipal requester) {
    return collectionRepository.findAllByUser(User.getUserReference(requester.userId())).stream()
        .map(collectionMapper::toDto)
        .toList();
  }

  @Transactional
  public CollectionDto createCollection(UserPrincipal requester, CreateCollectionRequest request) {
    User user = User.getUserReference(requester.userId());

    boolean isFirstCollection = collectionRepository.findDefaultCollectionByUser(user).isEmpty();

    Collection collection =
        Collection.builder().user(user).name(request.name()).isDefault(isFirstCollection).build();
    collection = collectionRepository.save(collection);

    return collectionMapper.toDto(collection);
  }

  @Transactional
  public void deleteCollection(UserPrincipal requester, String collectionId) {
    User user = User.getUserReference(requester.userId());

    if (collectionRepository.countByUser(user) == 1) {
      throw new IllegalStateException("User has no collections to delete.");
    }

    collectionRepository.deleteById(collectionId);
  }

  @Transactional
  public void addJournalToCollection(UserPrincipal requester, Journal journal, String collectionId)
      throws CollectionNotFoundException {
    Collection collection = collectionRepository.findById(collectionId).orElseThrow();

    CollectionEntry collectionEntry =
        CollectionEntry.builder().collection(collection).journal(journal).build();
    collection.getJournals().add(collectionEntry);

    collectionRepository.save(collection);
  }
}
