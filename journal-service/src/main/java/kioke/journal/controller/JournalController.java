package kioke.journal.controller;

import jakarta.validation.Valid;
import java.util.Optional;
import kioke.commons.http.HttpResponseBody;
import kioke.journal.dto.request.journal.CreateJournalRequestBodyDto;
import kioke.journal.dto.request.journal.MoveJournalRequestBodyDto;
import kioke.journal.dto.response.journal.CreateJournalResponseBodyDto;
import kioke.journal.dto.response.journal.GetJournalResponseBodyDto;
import kioke.journal.exception.journal.CannotCreateJournalInArchiveException;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.exception.permission.AccessDeniedException;
import kioke.journal.exception.shelf.ShelfNotFoundException;
import kioke.journal.exception.user.UserNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.model.Shelf;
import kioke.journal.model.User;
import kioke.journal.service.JournalPermissionService;
import kioke.journal.service.JournalService;
import kioke.journal.service.ShelfService;
import kioke.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journals")
public class JournalController {
  @Autowired @Lazy private JournalService journalService;
  @Autowired @Lazy private UserService userService;
  @Autowired @Lazy private JournalPermissionService journalPermissionService;
  @Autowired @Lazy private ShelfService shelfService;

  @PostMapping
  public ResponseEntity<CreateJournalResponseBodyDto> createJournal(
      @AuthenticationPrincipal String uid,
      @RequestBody @Valid CreateJournalRequestBodyDto requestBodyDto)
      throws UserNotFoundException, ShelfNotFoundException, CannotCreateJournalInArchiveException {
    User user = userService.getUserById(uid);
    Shelf shelf = shelfService.getShelfById(requestBodyDto.getShelfId());
    if (shelf.isArchive()) {
      throw new CannotCreateJournalInArchiveException();
    }

    Journal journal =
        journalService.createJournal(
            user, requestBodyDto.getTitle(), requestBodyDto.getDescription());
    shelfService.putJournalInShelf(journal, shelf);

    journalPermissionService.grantAuthorPermissionsToUser(user, journal);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(CreateJournalResponseBodyDto.from(journal));
  }

  @GetMapping("/{jid}")
  public ResponseEntity<HttpResponseBody<GetJournalResponseBodyDto>> getJournal(
      @AuthenticationPrincipal String uid,
      @RequestAttribute String requestId,
      @PathVariable String jid)
      throws UserNotFoundException, JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(jid);

    journalPermissionService.checkReadPermissions(user, journal);

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            HttpResponseBody.success(
                requestId, HttpStatus.OK, GetJournalResponseBodyDto.from(journal)));
  }

  @PutMapping("/{jid}/shelf")
  public ResponseEntity<Void> moveJournal(
      @AuthenticationPrincipal String uid,
      @PathVariable String jid,
      @RequestBody @Valid MoveJournalRequestBodyDto requestBodyDto)
      throws AccessDeniedException,
          UserNotFoundException,
          JournalNotFoundException,
          ShelfNotFoundException {
    User user = userService.getUserById(uid);

    Journal journal = journalService.getJournalById(jid);
    journalPermissionService.checkReadPermissions(user, journal);

    Shelf shelf = shelfService.getShelfById(requestBodyDto.getShelfId());
    if (!shelf.getOwner().equals(user)) {
      throw new AccessDeniedException();
    }

    shelfService.putJournalInShelf(journal, shelf);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }

  @DeleteMapping("/{jid}")
  public ResponseEntity<Void> deleteJournal(
      @AuthenticationPrincipal String uid, @PathVariable String jid)
      throws UserNotFoundException, JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(jid);

    journalPermissionService.checkDeletePermissions(user, journal);

    Optional<Journal> deletedJournal = journalService.deleteJournal(user, journal);

    if (deletedJournal.isPresent()) {
      Shelf archive = shelfService.getArchive(user);
      shelfService.putJournalInShelf(journal, archive);
    }

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }
}
