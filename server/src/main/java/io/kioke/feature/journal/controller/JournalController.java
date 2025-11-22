package io.kioke.feature.journal.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.dto.CreateJournalRequest;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.request.GetJournalsParams;
import io.kioke.feature.journal.dto.request.UpdateJournalRequest;
import io.kioke.feature.journal.dto.response.GetJournalsResponse;
import io.kioke.feature.journal.service.JournalService;
import io.kioke.feature.user.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JournalController {

  private final JournalService journalService;

  @GetMapping("/journals/{journalId}")
  @ResponseStatus(HttpStatus.OK)
  public JournalDto getJournalById(@PathVariable String journalId) throws JournalNotFoundException {
    return journalService.getJournalById(journalId);
  }

  @GetMapping("/journals")
  @PreAuthorize("isAuthenticated()")
  public GetJournalsResponse getJournals(
      @AuthenticatedUser UserPrincipal user, @ModelAttribute GetJournalsParams params) {
    return journalService.getJournals(user.userId(), params);
  }

  @PostMapping("/journals")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("isAuthenticated()")
  public JournalDto createJournal(
      @AuthenticatedUser UserPrincipal user,
      @RequestBody @Validated CreateJournalRequest requestBody)
      throws CollectionNotFoundException {
    return journalService.createJournal(user, requestBody);
  }

  @PatchMapping("/journals/{journalId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateJournal(
      @AuthenticatedUser UserPrincipal user,
      @PathVariable String journalId,
      @RequestBody @Validated UpdateJournalRequest requestBody)
      throws JournalNotFoundException {
    journalService.updateJournal(user, journalId, requestBody);
  }

  @DeleteMapping("/journals/{journalId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteJournal(@PathVariable String journalId) {
    journalService.deleteJournal(journalId);
  }
}
