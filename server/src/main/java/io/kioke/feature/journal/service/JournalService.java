package io.kioke.feature.journal.service;

import io.kioke.constant.Permission;
import io.kioke.exception.AccessDeniedException;
import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.collection.service.CollectionService;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.request.CreateJournalRequestDto;
import io.kioke.feature.journal.dto.request.UpdateJournalRequestDto;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalService {

  private final JournalPermissionService journalPermissionService;
  private final CollectionService collectionService;
  private final JournalRepository journalRepository;
  private final JournalMapper journalMapper;

  public JournalService(
      JournalPermissionService journalPermissionService,
      CollectionService collectionService,
      JournalRepository journalRepository,
      JournalMapper journalMapper) {
    this.journalPermissionService = journalPermissionService;
    this.collectionService = collectionService;
    this.journalRepository = journalRepository;
    this.journalMapper = journalMapper;
  }

  @Transactional(rollbackFor = Exception.class)
  public JournalDto createJournal(UserDto userDto, CreateJournalRequestDto request)
      throws AccessDeniedException, CollectionNotFoundException {
    User user = User.builder().userId(userDto.userId()).build();

    Journal journal = Journal.builder().author(user).title(request.title()).build();
    journal = journalRepository.save(journal);

    collectionService.addEntryToCollection(user, journal, request.collectionId());
    return journalMapper.toDto(journal);
  }

  @Transactional(readOnly = true)
  public JournalDto getJournal(UserDto user, String journalId)
      throws JournalNotFoundException, AccessDeniedException {
    Journal journal =
        journalRepository.findById(journalId).orElseThrow(() -> new JournalNotFoundException());

    journalPermissionService.checkPermissions(
        User.builder().userId(user.userId()).build(), journal, Set.of(Permission.READ));

    return journalMapper.toDto(journal);
  }

  @Transactional
  public void updateJournal(UserDto user, String journalId, UpdateJournalRequestDto request)
      throws JournalNotFoundException, AccessDeniedException {
    Journal journal =
        journalRepository.findById(journalId).orElseThrow(() -> new JournalNotFoundException());

    journalPermissionService.checkPermissions(
        User.builder().userId(user.userId()).build(), journal, Set.of(Permission.EDIT));

    if (request.title() != null) {
      journal.changeTitle(request.title());
    }

    if (request.description() != null) {
      journal.changeDescription(request.description());
    }

    journalRepository.save(journal);
  }
}
