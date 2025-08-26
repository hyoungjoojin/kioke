package io.kioke.feature.journal.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.exception.auth.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.dto.request.ShareJournalRequestDto;
import io.kioke.feature.journal.service.JournalService;
import io.kioke.feature.journal.service.ShareService;
import io.kioke.feature.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShareController {

  private final JournalService journalService;
  private final ShareService shareService;

  public ShareController(JournalService journalService, ShareService shareService) {
    this.journalService = journalService;
    this.shareService = shareService;
  }

  @PostMapping("/journals/{journalId}/share")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void shareJournal(
      @AuthenticatedUser UserDto user,
      @PathVariable String journalId,
      @RequestBody @Validated ShareJournalRequestDto requestBody)
      throws JournalNotFoundException, AccessDeniedException {
    shareService.createShareRequest(user, journalId, requestBody);
  }

  @PutMapping("/journals/{journalId}/share")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void acceptShareRequest(
      @AuthenticatedUser UserDto user,
      @PathVariable String journalId,
      @RequestBody @Validated ShareJournalRequestDto requestBody) {}

  @DeleteMapping("/journals/{journalId}/share")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void rejectShareRequest(
      @AuthenticatedUser UserDto user,
      @PathVariable String journalId,
      @RequestBody @Validated ShareJournalRequestDto requestBody) {}

  @PatchMapping("/journals/{journalId}/share")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateSharedUser(
      @AuthenticatedUser UserDto user,
      @PathVariable String journalId,
      @PathVariable String userId) {
    // Need to update status for shared user, can delete
  }
}
