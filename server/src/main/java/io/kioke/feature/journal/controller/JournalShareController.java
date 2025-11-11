package io.kioke.feature.journal.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.exception.auth.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.dto.request.ShareJournalRequest;
import io.kioke.feature.user.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JournalShareController {

  @PostMapping("/journals/{journalId}/share")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void shareJournal(
      @AuthenticatedUser UserPrincipal user,
      @PathVariable String journalId,
      @RequestBody @Validated ShareJournalRequest requestBody)
      throws JournalNotFoundException, AccessDeniedException {}

  @PatchMapping("/journals/{journalId}/share")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void respondToShareJournal(
      @AuthenticatedUser UserPrincipal user,
      @PathVariable String journalId,
      @RequestParam String action) {}
}
