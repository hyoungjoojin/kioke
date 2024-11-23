package com.kioke.journal.controller;

import com.kioke.journal.dto.data.journal.CreateJournalDto;
import com.kioke.journal.dto.request.journal.*;
import com.kioke.journal.dto.response.ResponseDto;
import com.kioke.journal.dto.response.data.EmptyResponseDataDto;
import com.kioke.journal.dto.response.data.journal.*;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.service.JournalService;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Optional;
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

  @PostMapping
  public ResponseEntity<ResponseDto<CreateJournalResponseDataDto>> createJournal(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      @RequestBody @Valid CreateJournalRequestBodyDto requestBody)
      throws Exception {
    Journal journal = journalService.createJournal(CreateJournalDto.from(requestBody));
    CreateJournalResponseDataDto data = CreateJournalResponseDataDto.from(journal);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            ResponseDto.<CreateJournalResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(HttpStatus.CREATED.value())
                .success(true)
                .data(Optional.of(data))
                .error(Optional.empty())
                .build());
  }

  @GetMapping("/{jid}")
  public ResponseEntity<ResponseDto<GetJournalResponseDataDto>> getJournalById(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      @PathVariable String jid)
      throws JournalNotFoundException, Exception {
    Journal journal = journalService.getJournalById(jid);
    GetJournalResponseDataDto data = GetJournalResponseDataDto.from(journal);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            ResponseDto.<GetJournalResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(HttpStatus.OK.value())
                .success(true)
                .data(Optional.of(data))
                .error(Optional.empty())
                .build());
  }

  @DeleteMapping("/{jid}")
  public ResponseEntity<ResponseDto<EmptyResponseDataDto>> deleteJournalById(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      @PathVariable String jid)
      throws JournalNotFoundException, Exception {
    journalService.deleteJournalById(jid);

    return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            ResponseDto.<EmptyResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(HttpStatus.OK.value())
                .success(true)
                .data(Optional.empty())
                .error(Optional.empty())
                .build());
  }
}
