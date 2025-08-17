package io.kioke.feature.journal.service;

import io.kioke.constant.Permission;
import io.kioke.exception.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.request.CreateJournalRequestDto;
import io.kioke.feature.journal.dto.request.UpdateJournalRequestDto;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalService {

  private final JournalAuthService journalAuthService;
  private final JournalCollectionService journalCollectionService;

  private final JournalRepository journalRepository;

  public JournalService(
      JournalAuthService journalAuthService,
      JournalCollectionService journalCollectionService,
      JournalRepository journalRepository) {
    this.journalAuthService = journalAuthService;
    this.journalCollectionService = journalCollectionService;
    this.journalRepository = journalRepository;
  }

  @Transactional(readOnly = true)
  public JournalDto getJournal(UserDto user, String journalId)
      throws JournalNotFoundException, AccessDeniedException {
    JournalDto journal =
        journalRepository
            .findByJournalId(journalId)
            .orElseThrow(() -> new JournalNotFoundException());

    journalAuthService.checkPermissions(user, journal, Permission.READ);
    return journal;
  }

  @Transactional
  public JournalDto createJournal(UserDto userDto, CreateJournalRequestDto request) {
    User user = new User();
    user.setUserId(userDto.userId());

    Journal journal = new Journal();
    journal.setAuthor(user);
    journal.setTitle(request.title());
    journal.setDescription("");
    journal.setIsPublic(false);
    journal = journalRepository.saveAndFlush(journal);

    JournalDto journalDto = new JournalDto(journal.getJournalId());
    journalAuthService.setPermissions(
        userDto, journalDto, List.of(Permission.READ, Permission.EDIT, Permission.DELETE));

    journalCollectionService.addJournal(userDto, journalDto, request.collectionId());
    return journalDto;
  }

  @Transactional
  public void updateJournal(UserDto user, String journalId, UpdateJournalRequestDto request)
      throws JournalNotFoundException, AccessDeniedException {
    journalAuthService.checkPermissions(user, new JournalDto(journalId), Permission.EDIT);

    Journal journal = journalRepository.getReferenceById(journalId);
    if (request.title() != null) {
      journal.setTitle(request.title());
    }

    if (request.description() != null) {
      journal.setDescription(request.description());
    }

    journalRepository.save(journal);
  }
}
