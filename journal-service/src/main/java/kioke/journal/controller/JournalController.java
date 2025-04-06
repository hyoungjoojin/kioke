package kioke.journal.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Optional;
import kioke.commons.exception.security.AccessDeniedException;
import kioke.commons.http.HttpResponseBody;
import kioke.journal.constant.Permission;
import kioke.journal.constant.Role;
import kioke.journal.dto.request.journal.CreateJournalRequestBodyDto;
import kioke.journal.dto.request.journal.MoveJournalRequestBodyDto;
import kioke.journal.dto.request.journal.ShareJournalRequestBodyDto;
import kioke.journal.dto.request.journal.UnshareJournalRequestBodyDto;
import kioke.journal.dto.response.journal.CreateJournalResponseBodyDto;
import kioke.journal.dto.response.journal.GetBookmarksResponseBodyDto;
import kioke.journal.dto.response.journal.GetJournalResponseBodyDto;
import kioke.journal.exception.journal.CannotCreateJournalInArchiveException;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.exception.shelf.ShelfNotFoundException;
import kioke.journal.model.Journal;
import kioke.journal.model.Shelf;
import kioke.journal.model.User;
import kioke.journal.service.BookmarkService;
import kioke.journal.service.JournalRoleService;
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
  @Autowired private JournalRoleService journalRoleService;
  @Autowired @Lazy private ShelfService shelfService;
  @Autowired private BookmarkService bookmarkService;

  @GetMapping("/{journalId}")
  public ResponseEntity<HttpResponseBody<GetJournalResponseBodyDto>> getJournal(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      HttpServletRequest request) throws UsernameNotFoundException, JournalNotFoundException {
    User user = userService.getUserById(userId);
    Journal journal = journalService.getJournalById(journalId);

    if (!journalRoleService.hasPermission(user, journal, Permission.READ)) {
      throw new JournalNotFoundException(journalId);
    }

    HttpStatus status = HttpStatus.OK;
    GetJournalResponseBodyDto data = GetJournalResponseBodyDto.from(journal, bookmarkService.isBookmarked(user, journal));

    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, data));
  }

  @GetMapping("/bookmarks")
  public ResponseEntity<HttpResponseBody<GetBookmarksResponseBodyDto>> getBookmarks(
      @AuthenticationPrincipal String userId, HttpServletRequest request)
      throws UsernameNotFoundException {
    User user = userService.getUserById(userId);

    HttpStatus status = HttpStatus.OK;
    return ResponseEntity.status(status)
        .body(
            HttpResponseBody.success(
                request, status, GetBookmarksResponseBodyDto.from(user.getBookmarks())));
  }

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

    journalRoleService.setRole(user, journal, Role.AUTHOR);

    HttpStatus status = HttpStatus.CREATED;
    CreateJournalResponseBodyDto data = CreateJournalResponseBodyDto.from(journal);

    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_JSON)
        .body(HttpResponseBody.success(request, status, data));
  }

  @PostMapping("/{journalId}/bookmark")
  public ResponseEntity<HttpResponseBody<Void>> addBookmark(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      HttpServletRequest request)
      throws UsernameNotFoundException, JournalNotFoundException {
    User user = userService.getUserById(userId);
    Journal journal = journalService.getJournalById(journalId);

    bookmarkService.addBookmark(user, journal);

    HttpStatus status = HttpStatus.CREATED;
    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, null));
  }

  @PostMapping("/{journalId}/share")
  public ResponseEntity<HttpResponseBody<Void>> shareJournal(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      @RequestBody @Valid ShareJournalRequestBodyDto requestBodyDto,
      HttpServletRequest request)
      throws UsernameNotFoundException, JournalNotFoundException {
    User user = userService.getUserById(userId);
    Journal journal = journalService.getJournalById(journalId);

    if (!journalRoleService.hasPermission(user, journal, Permission.SHARE)) {}

    User invitee = userService.getUserById(requestBodyDto.userId());
    journalRoleService.setRole(invitee, journal, requestBodyDto.role());

    HttpStatus status = HttpStatus.CREATED;
    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, null));
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
    journalRoleService.hasPermission(user, journal, Permission.READ);

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
      throws UsernameNotFoundException, JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(jid);

    if (!journalRoleService.hasPermission(user, journal, Permission.DELETE)) {
      throw new AccessDeniedException();
    }

    Optional<Journal> deletedJournal = journalService.deleteJournal(user, journal);

    if (deletedJournal.isPresent()) {
      Shelf archive = shelfService.getArchive(user);
      shelfService.putJournalInShelf(journal, archive);
    }

    HttpStatus status = HttpStatus.NO_CONTENT;
    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, null));
  }

  @DeleteMapping("/{journalId}/bookmark")
  public ResponseEntity<HttpResponseBody<Void>> deleteBookmark(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      HttpServletRequest request)
      throws UsernameNotFoundException, JournalNotFoundException {
    User user = userService.getUserById(userId);
    Journal journal = journalService.getJournalById(journalId);
    bookmarkService.deleteBookmark(user, journal);

    HttpStatus status = HttpStatus.NO_CONTENT;
    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, null));
  }

  @DeleteMapping("/{journalId}/share")
  public ResponseEntity<HttpResponseBody<Void>> unshareJournal(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      @RequestBody @Valid UnshareJournalRequestBodyDto requestBodyDto,
      HttpServletRequest request)
      throws UsernameNotFoundException, JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(userId);
    Journal journal = journalService.getJournalById(journalId);

    if (!journalRoleService.hasPermission(user, journal, Permission.SHARE)) {
      throw new AccessDeniedException();
    }

    User invitee = userService.getUserById(requestBodyDto.userId());
    journalRoleService.deleteRole(invitee, journal);

    HttpStatus status = HttpStatus.CREATED;
    return ResponseEntity.status(status).body(HttpResponseBody.success(request, status, null));
  }
}
