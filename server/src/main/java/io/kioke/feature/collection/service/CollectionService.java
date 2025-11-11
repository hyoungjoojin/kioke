package io.kioke.feature.collection.service;

import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.feature.collection.domain.Collection;
import io.kioke.feature.collection.dto.CollectionDto;
import io.kioke.feature.collection.dto.CreateCollectionRequest;
import io.kioke.feature.collection.repository.CollectionRepository;
import io.kioke.feature.collection.util.CollectionMapper;
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
    Collection collection =
        Collection.builder()
            .user(User.getUserReference(requester.userId()))
            .name(request.name())
            .build();
    collection = collectionRepository.save(collection);

    return collectionMapper.toDto(collection);
  }

  @Transactional
  public void deleteCollection(String collectionId) {
    collectionRepository.deleteById(collectionId);
  }

  // @Transactional
  // public void addEntryToCollection(String userId, String journalId, String collectionId)
  //     throws CollectionNotFoundException {
  //   Collection collection =
  //       collectionRepository
  //           .findByUserAndId(userId, collectionId)
  //           .orElseThrow(() -> new CollectionNotFoundException());
  //   collection.addJournal(journal);
  //   collectionRepository.save(collection);
  // }
}
