package kioke.journal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kioke.commons.exception.security.AccessDeniedException;
import kioke.journal.constant.Permission;
import kioke.journal.constant.Role;
import kioke.journal.dto.request.journal.UpdateJournalRequestBodyDto;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.exception.shelf.ShelfNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.model.Shelf;
import kioke.journal.model.User;
import kioke.journal.repository.JournalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class JournalService {

  private final UserService userService;
  private final BookmarkService bookmarkService;
  private final ShelfService shelfService;
  private final JournalRoleService journalRoleService;

  private final JournalRepository journalRepository;

  public JournalService(
      UserService userService,
      BookmarkService bookmarkService,
      ShelfService shelfService,
      JournalRoleService journalRoleService,
      JournalRepository journalRepository) {
    this.userService = userService;
    this.bookmarkService = bookmarkService;
    this.shelfService = shelfService;
    this.journalRoleService = journalRoleService;
    this.journalRepository = journalRepository;
  }

  @Transactional(readOnly = true)
  public List<Journal> getJournals(String userId, Boolean bookmarked) {
    Objects.requireNonNull(userId, "User ID should not be null.");

    List<String> journalIds;
    if (bookmarked) {
      journalIds = bookmarkService.getBookmarkedJournalIds(userId);
    } else {
      journalIds = journalRoleService.getJournalIds(userId);
    }

    return journalIds.stream()
        .map(
            journalId -> {
              try {
                return getJournalById(userId, journalId);
              } catch (JournalNotFoundException e) {
                return null;
              }
            })
        .filter(journal -> journal != null)
        .toList();
  }

  @Transactional(readOnly = true)
  public Journal getJournalById(String userId, String journalId) throws JournalNotFoundException {
    Objects.requireNonNull(userId, "User ID should not be null.");
    Objects.requireNonNull(journalId, "Journal ID should not be null.");

    if (!journalRoleService.hasPermission(userId, journalId, Permission.READ)) {
      log.debug("User has no permission to read the journal.");
      throw new JournalNotFoundException(journalId);
    }

    return journalRepository
        .findById(journalId)
        .orElseThrow(
            () -> {
              log.debug("Journal could not be found in the database.");
              return new JournalNotFoundException(journalId);
            });
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

    journalRoleService.setRole(user, journal, Role.AUTHOR);
    shelfService.setShelf(user, journal, shelf);

    return journal;
  }

  @Transactional
  public void shareJournal(String userId, String journalId, String inviteeUserId, Role role)
      throws JournalNotFoundException, AccessDeniedException {
    User invitee = userService.getUserById(inviteeUserId);

    Journal journal = getJournalById(userId, journalId);
    if (!journalRoleService.hasPermission(userId, journalId, Permission.SHARE)) {
      log.debug("User has no permission to share the journal.");
      throw new AccessDeniedException();
    }

    journalRoleService.setRole(invitee, journal, role);
  }

  @Transactional
  public void unshareJournal(String userId, String journalId, String inviteeUserId)
      throws JournalNotFoundException, AccessDeniedException {
    if (!journalRoleService.hasPermission(userId, journalId, Permission.SHARE)) {
      log.debug("User has no permission to share the journal.");
      throw new AccessDeniedException();
    }

    journalRoleService.deleteRole(inviteeUserId, journalId);
  }

  @Transactional(rollbackFor = Exception.class)
  public void updateJournal(
      String userId, String journalId, UpdateJournalRequestBodyDto requestBodyDto)
      throws JournalNotFoundException, AccessDeniedException, ShelfNotFoundException {
    User user = userService.getUserById(userId);
    Journal journal = getJournalById(userId, journalId);

    if (!journalRoleService.hasPermission(userId, journalId, Permission.WRITE)) {
      log.debug("User has no permission to update the journal.");
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
        bookmarkService.addBookmark(user, journal);
      } else {
        bookmarkService.deleteBookmark(user, journal);
      }
    }

    journalRepository.save(journal);
  }

  @Transactional
  public void deleteJournal(String userId, String journalId)
      throws JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(userId);
    Journal journal = getJournalById(userId, journalId);

    if (!journalRoleService.hasPermission(userId, journalId, Permission.DELETE)) {
      log.debug("User has no permission to delete the journal.");
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
