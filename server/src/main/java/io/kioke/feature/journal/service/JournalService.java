package io.kioke.feature.journal.service;

import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.request.CreateJournalRequest;
import io.kioke.feature.journal.dto.request.ShareJournalRequest;
import io.kioke.feature.journal.dto.request.UpdateJournalRequest;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.journal.repository.JournalShareRequestRepository;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalService {

  private final JournalRepository journalRepository;
  private final JournalShareRequestRepository journalShareRequestRepository;

  private final UserService userService;

  public JournalService(
      JournalRepository journalRepository,
      JournalShareRequestRepository journalShareRequestRepository,
      UserService userService) {
    this.journalRepository = journalRepository;
    this.journalShareRequestRepository = journalShareRequestRepository;
    this.userService = userService;
  }

  @PreAuthorize("hasPermission(#journalId, 'journal', 'READ')")
  @Transactional(readOnly = true)
  public Journal getJournal(String journalId) throws JournalNotFoundException {
    Journal journal =
        journalRepository
            .findWithUsersById(journalId)
            .orElseThrow(() -> new JournalNotFoundException());

    journal =
        journalRepository
            .findWithPagesById(journalId)
            .orElseThrow(() -> new JournalNotFoundException());

    return journal;
  }

  @Transactional(rollbackFor = Exception.class)
  @PreAuthorize("hasPermission('journal', 'CREATE')")
  public Journal createJournal(String userId, CreateJournalRequest request) {
    User user = userService.getUserReference(userId);

    Journal journal = Journal.builder().type(request.type()).title(request.title()).build();
    journal.addAuthor(user);

    journal = journalRepository.save(journal);
    return journal;
  }

  @Transactional
  @PreAuthorize("hasPermission(#journalId, 'journal', 'EDIT')")
  public void updateJournal(String journalId, UpdateJournalRequest request) {
    Journal journal = journalRepository.getReferenceById(journalId);

    if (request.title() != null) {
      journal.updateTitle(request.title());
    }

    if (request.description() != null) {
      journal.updateDescription(request.description());
    }

    journalRepository.save(journal);
  }

  @Transactional
  @PreAuthorize("hasPermission(#journalId, 'journal', 'DELETE')")
  public void deleteJournal(String journalId) {
    Journal journal = journalRepository.getReferenceById(journalId);
    journal.deleteJournal();
    journalRepository.save(journal);
  }

  @Transactional
  public void shareJournal(String requesterId, String journalId, ShareJournalRequest request) {}
}
