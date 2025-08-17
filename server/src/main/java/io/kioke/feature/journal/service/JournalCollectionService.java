package io.kioke.feature.journal.service;

import io.kioke.exception.journal.JournalCollectionNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalCollection;
import io.kioke.feature.journal.dto.JournalCollectionDto;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.repository.JournalCollectionRepository;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class JournalCollectionService {

  private final JournalRepository journalRepository;
  private final JournalCollectionRepository journalCollectionRepository;

  private final JournalMapper journalMapper;

  public JournalCollectionService(
      JournalRepository journalRepository,
      JournalCollectionRepository journalCollectionRepository,
      JournalMapper journalMapper) {
    this.journalRepository = journalRepository;
    this.journalCollectionRepository = journalCollectionRepository;
    this.journalMapper = journalMapper;
  }

  @Transactional(readOnly = true)
  public JournalCollectionDto getJournalCollection(UserDto user, String collectionId)
      throws JournalCollectionNotFoundException {
    Assert.notNull(user, "User must not be null");

    return journalRepository
        .findCollectionByUserAndId(user, collectionId)
        .orElseThrow(() -> new JournalCollectionNotFoundException());
  }

  @Transactional(readOnly = true)
  public List<JournalCollectionDto> getJournalCollections(UserDto user) {
    Assert.notNull(user, "User must not be null");
    return journalRepository.findCollectionsByUser(user);
  }

  @Transactional
  public void addJournal(UserDto user, JournalDto journal, String collectionId) {
    JournalCollection collection = journalCollectionRepository.getReferenceById(collectionId);

    Journal journalReference = new Journal();
    journalReference.setJournalId(journal.journalId());
    collection.getJournals().add(journalReference);

    journalCollectionRepository.save(collection);
  }

  @Transactional
  public JournalCollectionDto createJournalCollection(UserDto user, String name) {
    User userReference = new User();
    userReference.setUserId(user.userId());

    boolean isDefault = !journalCollectionRepository.existsDefaultCollection(userReference);

    JournalCollection collection = new JournalCollection();
    collection.setUser(userReference);
    collection.setName(name);
    collection.setIsDefaultCollection(isDefault);
    collection = journalCollectionRepository.save(collection);

    return journalMapper.toJournalCollectionDto(collection, new ArrayList<>());
  }
}
