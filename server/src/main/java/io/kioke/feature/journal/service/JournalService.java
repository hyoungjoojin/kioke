package io.kioke.feature.journal.service;

import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.collection.service.CollectionService;
import io.kioke.feature.image.domain.Image;
import io.kioke.feature.image.service.ImageService;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalRole;
import io.kioke.feature.journal.domain.JournalUser;
import io.kioke.feature.journal.dto.CreateJournalRequest;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.request.GetJournalsParams;
import io.kioke.feature.journal.dto.request.UpdateJournalRequest;
import io.kioke.feature.journal.dto.response.GetJournalsResponse;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.journal.repository.JournalUserRepository;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.media.service.MediaService;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserPrincipal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JournalService {

  private final CollectionService collectionService;
  private final ImageService imageService;
  private final MediaService mediaService;
  private final JournalRepository journalRepository;
  private final JournalUserRepository journalUserRepository;
  private final JournalMapper journalMapper;

  @PreAuthorize("hasPermission(#journalId, 'journal', 'read')")
  @Transactional(readOnly = true)
  public JournalDto getJournalById(String journalId) throws JournalNotFoundException {
    Journal journal =
        journalRepository.findById(journalId).orElseThrow(() -> new JournalNotFoundException());

    List<Page> pages = journalRepository.findPagesById(journalId);

    String coverUrl =
        journal.getCover() == null
            ? null
            : mediaService.getPresignedUrl(journal.getCover()).toExternalForm();

    return journalMapper.toDto(journal, pages, coverUrl);
  }

  @Transactional(readOnly = true)
  public GetJournalsResponse getJournals(String userId, GetJournalsParams params) {
    Pageable pageable = Pageable.ofSize(params.size() == 0 ? 20 : params.size());

    var journals =
        journalRepository.findAllByQuery(
            params.query() == null ? "" : params.query(), pageable, params.cursor());

    String cursor =
        journals.stream()
            .max((a, b) -> a.getId().compareTo(b.getId()))
            .map(Journal::getId)
            .orElse("");

    return new GetJournalsResponse(
        journals.stream()
            .map(journal -> new GetJournalsResponse.Journal(journal.getId(), journal.getTitle()))
            .toList(),
        cursor,
        journals.hasNext());
  }

  @Transactional(readOnly = true)
  public List<Journal> getJournalsByIds(List<String> journalIds) {
    // TODO: Need to implement batch fetching
    return journalIds.stream()
        .map(journalId -> journalRepository.findById(journalId).orElseThrow())
        .toList();
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

    return journalMapper.toDto(journal, new ArrayList<>(), null);
  }

  @Transactional
  @PreAuthorize("hasPermission(#journalId, 'journal', 'update')")
  public void updateJournal(
      UserPrincipal requester, String journalId, UpdateJournalRequest request) {
    Journal journal = journalRepository.getReferenceById(journalId);

    if (request.title() != null) {
      journal.setTitle(request.title());
    }

    if (request.description() != null) {
      journal.setDescription(request.description());
    }

    if (request.cover() != null) {
      Image image = imageService.getImage(request.cover());
      journal.setCover(image);
    }

    journalRepository.save(journal);
  }

  @Transactional
  @PreAuthorize("hasPermission(#journalId, 'journal', 'delete')")
  public void deleteJournal(String journalId) {
    journalRepository.deleteById(journalId);
  }
}
