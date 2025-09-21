package io.kioke.feature.journal.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.exception.auth.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.request.CreateJournalRequest;
import io.kioke.feature.journal.dto.request.ShareJournalRequest;
import io.kioke.feature.journal.dto.request.UpdateJournalRequest;
import io.kioke.feature.journal.dto.response.CreateJournalResponse;
import io.kioke.feature.journal.dto.response.JournalResponse;
import io.kioke.feature.journal.service.JournalService;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.user.dto.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JournalController {

  private final JournalService journalService;
  private final JournalMapper journalMapper;

  public JournalController(JournalService journalService, JournalMapper journalMapper) {
    this.journalService = journalService;
    this.journalMapper = journalMapper;
  }

  @GetMapping("/journals/{journalId}")
  @ResponseStatus(HttpStatus.OK)
  public JournalResponse getJournal(@PathVariable String journalId)
      throws JournalNotFoundException {
    Journal journal = journalService.getJournal(journalId);
    return journalMapper.mapToJournalResponse(journal);
  }

  @GetMapping("/journals")
  public Page<JournalResponse> getJournals(
      @AuthenticatedUser UserPrincipal user,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<Journal> journals = journalService.getJournalsByUser(user.userId(), page, size);
    return journals.map(journal -> journalMapper.mapToJournalResponse(journal));
  }

  @PostMapping("/journals")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateJournalResponse createJournal(
      @AuthenticatedUser UserPrincipal user,
      @RequestBody @Validated CreateJournalRequest requestBody) {
    Journal journal = journalService.createJournal(user.userId(), requestBody);
    return journalMapper.mapToCreateJournalResponse(user, journal);
  }

  @PatchMapping("/journals/{journalId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateJournal(
      @PathVariable String journalId, @RequestBody @Validated UpdateJournalRequest requestBody)
      throws JournalNotFoundException {
    journalService.updateJournal(journalId, requestBody);
  }

  @DeleteMapping("/journals/{journalId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteJournal(@PathVariable String journalId) {
    journalService.deleteJournal(journalId);
  }

  @PostMapping("/journals/{journalId}/share")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void shareJournal(
      @AuthenticatedUser UserPrincipal user,
      @PathVariable String journalId,
      @RequestBody @Validated ShareJournalRequest requestBody)
      throws JournalNotFoundException, AccessDeniedException {
    journalService.shareJournal(user.userId(), journalId, requestBody);
  }

  @PatchMapping("/journals/{journalId}/share")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void respondToShareJournal(
      @AuthenticatedUser UserPrincipal user,
      @PathVariable String journalId,
      @RequestParam String action) {}
}
