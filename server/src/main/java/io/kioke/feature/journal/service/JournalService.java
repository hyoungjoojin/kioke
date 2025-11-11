package io.kioke.feature.journal.service;

import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.collection.service.CollectionService;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalRole;
import io.kioke.feature.journal.domain.JournalUser;
import io.kioke.feature.journal.dto.CreateJournalRequest;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.request.UpdateJournalRequest;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserPrincipal;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JournalService {

  private final CollectionService collectionService;
  private final JournalRepository journalRepository;
  private final JournalMapper journalMapper;

  @PreAuthorize("hasPermission(#journalId, 'journal', 'read')")
  @Transactional(readOnly = true)
  public JournalDto getJournalById(String journalId) throws JournalNotFoundException {
    Journal journal =
        journalRepository.findById(journalId).orElseThrow(() -> new JournalNotFoundException());

    return journalMapper.toDto(journal);
  }

  @Transactional(rollbackFor = Exception.class)
  @PreAuthorize("hasPermission('journal', 'create')")
  public JournalDto createJournal(UserPrincipal requester, CreateJournalRequest request)
      throws CollectionNotFoundException {
    Journal journal =
        Journal.builder()
            .title(request.title())
            .description("")
            .users(new ArrayList<>())
            .isPublic(false)
            .build();
    journal = journalRepository.save(journal);

    journal
        .getUsers()
        .add(
            JournalUser.builder()
                .user(User.builder().userId(requester.userId()).build())
                .journal(journal)
                .role(JournalRole.AUTHOR)
                .build());
    journalRepository.save(journal);

    collectionService.addJournalToCollection(requester, journal, request.collectionId());

    return journalMapper.toDto(journal);
  }

  @Transactional
  @PreAuthorize("hasPermission(#journalId, 'journal', 'update')")
  public void updateJournal(String journalId, UpdateJournalRequest request) {
    Journal journal = journalRepository.getReferenceById(journalId);

    if (request.title() != null) {
      journal.setTitle(request.title());
    }

    if (request.description() != null) {
      journal.setDescription(request.description());
    }

    journalRepository.save(journal);
  }

  @Transactional
  @PreAuthorize("hasPermission(#journalId, 'journal', 'delete')")
  public void deleteJournal(String journalId) {
    journalRepository.deleteById(journalId);
  }
}
