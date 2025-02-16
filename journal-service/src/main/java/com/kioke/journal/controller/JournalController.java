package com.kioke.journal.controller;

import com.kioke.journal.dto.request.journal.CreateJournalRequestBodyDto;
import com.kioke.journal.dto.response.journal.CreateJournalResponseBodyDto;
import com.kioke.journal.dto.response.journal.GetJournalResponseBodyDto;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.exception.permission.AccessDeniedException;
import com.kioke.journal.exception.shelf.ShelfNotFoundException;
import com.kioke.journal.exception.user.UserNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Shelf;
import com.kioke.journal.model.User;
import com.kioke.journal.service.JournalPermissionService;
import com.kioke.journal.service.JournalService;
import com.kioke.journal.service.ShelfService;
import com.kioke.journal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
      @RequestAttribute(required = true, name = "uid") String uid,
      @RequestBody @Valid CreateJournalRequestBodyDto requestBodyDto)
      throws UserNotFoundException, ShelfNotFoundException {
    String shelfId = requestBodyDto.getShelfId(), title = requestBodyDto.getTitle();

    User user = userService.getUserById(uid);
    Shelf shelf = shelfService.getShelfById(shelfId);
    Journal journal = journalService.createJournal(user, shelf, title);

    journalPermissionService.grantAuthorPermissionsToUser(user, journal);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(CreateJournalResponseBodyDto.from(journal));
  }

  @GetMapping("/{jid}")
  public ResponseEntity<GetJournalResponseBodyDto> getJournal(
      @RequestAttribute(required = true, name = "uid") String uid, @PathVariable String jid)
      throws UserNotFoundException, JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(jid);

    journalPermissionService.checkReadPermissions(user, journal);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(GetJournalResponseBodyDto.from(journal));
  }

  @DeleteMapping("/{jid}")
  public ResponseEntity<Void> deleteJournal(
      @RequestAttribute(required = true, name = "uid") String uid, @PathVariable String jid)
      throws UserNotFoundException, JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(jid);

    journalPermissionService.checkDeletePermissions(user, journal);

    journalService.deleteJournal(journal);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
  }
}
