package io.kioke.feature.journal.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.exception.AccessDeniedException;
import io.kioke.exception.collection.CollectionNotFoundException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.dto.request.CreateJournalRequestDto;
import io.kioke.feature.journal.dto.request.UpdateJournalRequestDto;
import io.kioke.feature.journal.dto.response.CreateJournalResponseDto;
import io.kioke.feature.journal.dto.response.GetJournalResponseDto;
import io.kioke.feature.journal.service.JournalService;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/journals")
  @ResponseStatus(HttpStatus.CREATED)
  public CreateJournalResponseDto createJournal(
      @AuthenticatedUser UserDto user, @RequestBody @Validated CreateJournalRequestDto requestBody)
      throws AccessDeniedException, CollectionNotFoundException {
    JournalDto journal = journalService.createJournal(user, requestBody);
    return journalMapper.toCreateJournalResponse(journal);
  }

  @GetMapping("/journals/{journalId}")
  @ResponseStatus(HttpStatus.OK)
  public GetJournalResponseDto getJournal(
      @AuthenticatedUser UserDto user, @PathVariable String journalId)
      throws JournalNotFoundException, AccessDeniedException {
    JournalDto journal = journalService.getJournal(user, journalId);
    return journalMapper.toGetJournalResponse(journal);
  }

  @PatchMapping("/journals/{journalId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateJournal(
      @AuthenticatedUser UserDto user,
      @PathVariable String journalId,
      @RequestBody @Validated UpdateJournalRequestDto requestBody)
      throws JournalNotFoundException, AccessDeniedException {
    journalService.updateJournal(user, journalId, requestBody);
  }
}
