package io.kioke.feature.journal.service;

import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.image.domain.Image;
import io.kioke.feature.image.service.ImageService;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.JournalCoverImage;
import io.kioke.feature.journal.dto.request.CreateJournalRequest;
import io.kioke.feature.journal.dto.request.ShareJournalRequest;
import io.kioke.feature.journal.dto.request.UpdateJournalRequest;
import io.kioke.feature.journal.dto.response.JournalResponse;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.journal.repository.JournalShareRequestRepository;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JournalService {

  private final JournalRepository journalRepository;
  private final JournalShareRequestRepository journalShareRequestRepository;

  private final UserService userService;
  private final ImageService imageService;

  private final JournalMapper journalMapper;

  public JournalService(
      JournalRepository journalRepository,
      JournalShareRequestRepository journalShareRequestRepository,
      UserService userService,
      ImageService imageService,
      JournalMapper journalMapper) {
    this.journalRepository = journalRepository;
    this.journalShareRequestRepository = journalShareRequestRepository;
    this.userService = userService;
    this.imageService = imageService;
    this.journalMapper = journalMapper;
  }

  @PreAuthorize("hasPermission(#journalId, 'journal', 'READ')")
  @Transactional(readOnly = true)
  public JournalResponse getJournal(String journalId) throws JournalNotFoundException {
    Journal journal =
        journalRepository
            .findWithUsersById(journalId)
            .orElseThrow(() -> new JournalNotFoundException());

    journal =
        journalRepository
            .findWithPagesById(journalId)
            .orElseThrow(() -> new JournalNotFoundException());

    String coverUrl =
        journal.getCoverImage() != null
            ? imageService.getImageUrl(journal.getCoverImage().getImage().id())
            : null;

    return journalMapper.mapToJournalResponse(journal, coverUrl);
  }

  @Transactional(readOnly = true)
  @PreAuthorize("#userId == authentication.principal")
  public Page<JournalResponse> getJournalsByUser(String userId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<Journal> journals = journalRepository.findAllWithUsersByUserId(userId, pageable);
    journals = journalRepository.findAllWithPagesByUserId(userId, pageable);

    return journals.map(
        journal -> {
          String coverUrl =
              journal.getCoverImage() != null
                  ? imageService.getImageUrl(journal.getCoverImage().getImage().id())
                  : null;
          return journalMapper.mapToJournalResponse(journal, coverUrl);
        });
  }

  @Transactional(readOnly = true)
  public Journal getJournalReference(String journalId) {
    return journalRepository.getReferenceById(journalId);
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

    if (request.coverImage() != null) {
      Image image = imageService.uploadImageSuccess(request.coverImage());
      JournalCoverImage coverImage = JournalCoverImage.of(journal, image);
      journal.updateCover(coverImage);
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
