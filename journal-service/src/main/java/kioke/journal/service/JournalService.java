package kioke.journal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kioke.commons.exception.security.AccessDeniedException;
import kioke.journal.constant.Permission;
import kioke.journal.constant.Role;
import kioke.journal.dto.data.journal.JournalDto;
import kioke.journal.dto.data.journal.JournalPreviewDto;
import kioke.journal.dto.request.journal.UpdateJournalRequestBodyDto;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.exception.shelf.ShelfNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.model.Shelf;
import kioke.journal.model.User;
import kioke.journal.model.UserJournalMetadata;
import kioke.journal.repository.JournalRepository;
import kioke.journal.repository.UserJournalMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class JournalService {

  private final UserService userService;
  private final UserJournalMetadataService userJournalMetadataService;
  private final ShelfService shelfService;

  private final JournalRepository journalRepository;
  private final UserJournalMetadataRepository userJournalMetadataRepository;

  public JournalService(
      UserService userService,
      UserJournalMetadataService userJournalMetadataService,
      ShelfService shelfService,
      JournalRepository journalRepository,
      UserJournalMetadataRepository userJournalMetadataRepository) {
    this.userService = userService;
    this.userJournalMetadataService = userJournalMetadataService;
    this.shelfService = shelfService;
    this.journalRepository = journalRepository;
    this.userJournalMetadataRepository = userJournalMetadataRepository;
  }

  @Transactional(readOnly = true)
  public List<JournalPreviewDto> getJournals(String userId, boolean findOnlyBookmarkedJournals) {
    if (userId == null) {
      throw new IllegalArgumentException("User ID should not be null.");
    }

    log.debug("start fetching journals from database");
    List<JournalPreviewDto> journals =
        userJournalMetadataRepository.findAllJournalsByUser(userId, findOnlyBookmarkedJournals);
    log.debug("finished fetching journals from database");

    return journals;
  }

  @Transactional(readOnly = true)
  public JournalDto getJournalById(String userId, String journalId)
      throws JournalNotFoundException {
    Objects.requireNonNull(userId, "User ID should not be null.");
    Objects.requireNonNull(journalId, "Journal ID should not be null.");

    if (!userJournalMetadataService.hasPermission(userId, journalId, Permission.READ)) {
      throw new JournalNotFoundException(journalId);
    }

    Journal journal =
        journalRepository
            .findById(journalId)
            .orElseThrow(
                () -> {
                  log.debug("Journal could not be found in the database.");
                  return new JournalNotFoundException(journalId);
                });

    UserJournalMetadata userJournalMetadata =
        userJournalMetadataService.getJournal(userId, journalId).get();

    return JournalDto.from(journal, userJournalMetadata);
  }

  @Transactional(rollbackFor = Exception.class)
  public Journal createJournal(String userId, String shelfId, String title, String description)
      throws ShelfNotFoundException {
    Objects.requireNonNull(userId, "User ID should not be null.");

    User user = userService.getUserById(userId);

    Shelf shelf = shelfService.getShelfById(userId, shelfId);

    Journal journal =
        Journal.builder()
            .title(Objects.requireNonNullElse(title, ""))
            .description(Objects.requireNonNullElse(description, ""))
            .users(new ArrayList<>())
            .shelves(new ArrayList<>())
            .isDeleted(false)
            .build();

    journal = journalRepository.save(journal);

    userJournalMetadataService.setRole(user, journal, Role.AUTHOR);
    shelfService.setShelf(user, journal, shelf);

    return journal;
  }

  @Transactional
  public void shareJournal(String userId, String journalId, String inviteeUserId, Role role)
      throws JournalNotFoundException, AccessDeniedException {
    User invitee = userService.getUserById(inviteeUserId);

    Journal journal =
        journalRepository
            .findById(journalId)
            .orElseThrow(() -> new JournalNotFoundException(journalId));

    if (!userJournalMetadataService.hasPermission(userId, journalId, Permission.SHARE)) {
      log.debug("User has no permission to share the journal.");
      throw new AccessDeniedException();
    }

    userJournalMetadataService.setRole(invitee, journal, role);
  }

  @Transactional
  public void unshareJournal(String userId, String journalId, String inviteeUserId)
      throws JournalNotFoundException, AccessDeniedException {
    if (!userJournalMetadataService.hasPermission(userId, journalId, Permission.SHARE)) {
      log.debug("User has no permission to share the journal.");
      throw new AccessDeniedException();
    }

    userJournalMetadataService.deleteRole(inviteeUserId, journalId);
  }

  @Transactional(rollbackFor = Exception.class)
  public void updateJournal(
      String userId, String journalId, UpdateJournalRequestBodyDto requestBodyDto)
      throws JournalNotFoundException, AccessDeniedException, ShelfNotFoundException {
    User user = userService.getUserById(userId);

    Journal journal =
        journalRepository
            .findById(journalId)
            .orElseThrow(() -> new JournalNotFoundException(journalId));

    if (!userJournalMetadataService.hasPermission(userId, journalId, Permission.WRITE)) {
      throw new AccessDeniedException();
    }

    if (requestBodyDto.shelfId() != null) {
      Shelf shelf = shelfService.getShelfById(userId, requestBodyDto.shelfId());
      shelfService.setShelf(user, journal, shelf);
    }

    if (requestBodyDto.title() != null) {
      journal.setTitle(requestBodyDto.title());
    }

    if (requestBodyDto.description() != null) {
      journal.setDescription(requestBodyDto.description());
    }

    if (requestBodyDto.bookmark() != null) {
      if (requestBodyDto.bookmark()) {
        userJournalMetadataService.addBookmark(userId, journalId);
      } else {
        userJournalMetadataService.deleteBookmark(userId, journalId);
      }
    }

    journalRepository.save(journal);
  }

  @Transactional
  public void deleteJournal(String userId, String journalId)
      throws JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(userId);
    Journal journal =
        journalRepository
            .findById(journalId)
            .orElseThrow(() -> new JournalNotFoundException(journalId));

    if (!userJournalMetadataService.hasPermission(userId, journalId, Permission.DELETE)) {
      throw new AccessDeniedException();
    }

    if (journal.isDeleted()) {
      journalRepository.delete(journal);
      return;
    }

    journal.setDeleted(true);
    journal = journalRepository.save(journal);

    Shelf archiveShelf = shelfService.getArchiveShelf(user);
    shelfService.setShelf(user, journal, archiveShelf);
  }
}
