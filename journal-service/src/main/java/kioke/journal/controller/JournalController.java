package kioke.journal.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Optional;
import kioke.commons.http.HttpResponseBody;
import kioke.journal.dto.request.journal.CreateJournalRequestBodyDto;
import kioke.journal.dto.request.journal.MoveJournalRequestBodyDto;
import kioke.journal.dto.response.journal.CreateJournalResponseBodyDto;
import kioke.journal.dto.response.journal.GetJournalResponseBodyDto;
import kioke.journal.exception.journal.CannotCreateJournalInArchiveException;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.exception.permission.NoDeletePermissionsException;
import kioke.journal.exception.shelf.ShelfNotFoundException;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journals")
public class JournalController {
  @Autowired @Lazy private JournalService journalService;
  @Autowired @Lazy private UserService userService;
  @Autowired @Lazy private JournalPermissionService journalPermissionService;
  @Autowired @Lazy private ShelfService shelfService;

  @PostMapping
  public ResponseEntity<HttpResponseBody<CreateJournalResponseBodyDto>> createJournal(
      @AuthenticationPrincipal String uid,
      @RequestBody @Valid CreateJournalRequestBodyDto requestBodyDto,
      HttpServletRequest request)
      throws UsernameNotFoundException,
          ShelfNotFoundException,
          CannotCreateJournalInArchiveException {
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

    HttpStatus status = HttpStatus.CREATED;
    CreateJournalResponseBodyDto data = CreateJournalResponseBodyDto.from(journal);

    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(HttpResponseBody.success(request, status, data));
  }

  @GetMapping("/{jid}")
  public ResponseEntity<HttpResponseBody<GetJournalResponseBodyDto>> getJournal(
      @AuthenticationPrincipal String uid,
      @RequestAttribute String requestId,
      @PathVariable String jid,
      HttpServletRequest request)
      throws UsernameNotFoundException, JournalNotFoundException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(jid);

    journalPermissionService.checkReadPermissions(user, journal);

    HttpStatus status = HttpStatus.OK;
    GetJournalResponseBodyDto data = GetJournalResponseBodyDto.from(journal);

    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, data));
  }

  @PutMapping("/{jid}/shelf")
  public ResponseEntity<HttpResponseBody<Void>> moveJournal(
      @AuthenticationPrincipal String uid,
      @PathVariable String jid,
      @RequestBody @Valid MoveJournalRequestBodyDto requestBodyDto,
      HttpServletRequest request)
      throws UsernameNotFoundException, JournalNotFoundException, ShelfNotFoundException {
    User user = userService.getUserById(uid);

    Journal journal = journalService.getJournalById(jid);
    journalPermissionService.checkReadPermissions(user, journal);

    Shelf shelf = shelfService.getShelfById(requestBodyDto.getShelfId());
    if (!shelf.getOwner().equals(user)) {
      throw new ShelfNotFoundException(shelf.getId());
    }

    shelfService.putJournalInShelf(journal, shelf);

    HttpStatus status = HttpStatus.NO_CONTENT;
    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, null));
  }

  @DeleteMapping("/{jid}")
  public ResponseEntity<HttpResponseBody<Void>> deleteJournal(
      @AuthenticationPrincipal String uid, @PathVariable String jid, HttpServletRequest request)
      throws UsernameNotFoundException, JournalNotFoundException, NoDeletePermissionsException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(jid);

    journalPermissionService.checkDeletePermissions(user, journal);

    Optional<Journal> deletedJournal = journalService.deleteJournal(user, journal);

    if (deletedJournal.isPresent()) {
      Shelf archive = shelfService.getArchive(user);
      shelfService.putJournalInShelf(journal, archive);
    }

    HttpStatus status = HttpStatus.NO_CONTENT;
    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, null));
  }
}
