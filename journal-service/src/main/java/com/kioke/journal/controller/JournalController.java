package com.kioke.journal.controller;

import com.kioke.journal.constant.ErrorCode;
import com.kioke.journal.dto.request.CreateJournalRequestBodyDto;
import com.kioke.journal.dto.response.ResponseDto;
import com.kioke.journal.dto.response.data.CreateJournalResponseDataDto;
import com.kioke.journal.dto.response.data.EmptyResponseDataDto;
import com.kioke.journal.dto.response.data.GetJournalResponseDataDto;
import com.kioke.journal.dto.response.error.ResponseErrorDto;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.service.JournalService;
import com.kioke.journal.util.CustomLogger;
import jakarta.validation.Valid;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journals")
public class JournalController {
  @Autowired @Lazy private JournalService journalService;
  private static final CustomLogger log = new CustomLogger(JournalController.class);

  @PostMapping
  public ResponseEntity<ResponseDto<CreateJournalResponseDataDto>> createJournal(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      @RequestBody @Valid CreateJournalRequestBodyDto requestBody) {
    log.info(requestId, "HTTP POST " + path);

    Optional<CreateJournalResponseDataDto> data = Optional.empty();
    Optional<ResponseErrorDto> error = Optional.empty();

    HttpStatus status;
    MediaType contentType = MediaType.APPLICATION_JSON;

    try {
      Journal journal =
          journalService.createJournal(requestBody.getTitle(), requestBody.getTemplate());
      data = Optional.of(CreateJournalResponseDataDto.from(journal));

      status = HttpStatus.CREATED;
      contentType = MediaType.APPLICATION_JSON;

    } catch (Exception e) {
      log.error(requestId, e.toString());
      error =
          Optional.of(ResponseErrorDto.from(ErrorCode.INTERNAL_SERVER_ERROR, e.toString(), path));

      status = HttpStatus.INTERNAL_SERVER_ERROR;
      contentType = MediaType.APPLICATION_PROBLEM_JSON;
    }

    OffsetDateTime end = OffsetDateTime.now();
    log.info(
        requestId, "HTTP response took " + Duration.between(timestamp, end).toMillisPart() + "ms");

    return ResponseEntity.status(status)
        .contentType(contentType)
        .body(
            ResponseDto.<CreateJournalResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(status.value())
                .success(error.isEmpty())
                .data(data)
                .error(error)
                .build());
  }

  @GetMapping("/{jid}")
  public ResponseEntity<ResponseDto<GetJournalResponseDataDto>> getJournalById(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      @PathVariable String jid) {
    log.info(requestId, "HTTP GET " + path);

    Optional<GetJournalResponseDataDto> data = Optional.empty();
    Optional<ResponseErrorDto> error = Optional.empty();

    HttpStatus status;
    MediaType contentType = MediaType.APPLICATION_JSON;

    try {
      Journal journal = journalService.getJournalById(jid);
      data = Optional.of(GetJournalResponseDataDto.from(journal));

      status = HttpStatus.OK;

    } catch (JournalNotFoundException e) {
      log.info(requestId, e.toString());
      error = Optional.of(ResponseErrorDto.from(ErrorCode.JOURNAL_NOT_FOUND, e.toString(), path));

      status = HttpStatus.NOT_FOUND;
      contentType = MediaType.APPLICATION_PROBLEM_JSON;

    } catch (Exception e) {
      log.error(requestId, e.toString());
      error =
          Optional.of(ResponseErrorDto.from(ErrorCode.INTERNAL_SERVER_ERROR, e.toString(), path));

      status = HttpStatus.INTERNAL_SERVER_ERROR;
      contentType = MediaType.APPLICATION_PROBLEM_JSON;
    }

    OffsetDateTime end = OffsetDateTime.now();
    log.info(
        requestId, "HTTP response took " + Duration.between(timestamp, end).toMillisPart() + "ms");

    return ResponseEntity.status(status)
        .contentType(contentType)
        .body(
            ResponseDto.<GetJournalResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(status.value())
                .success(error.isEmpty())
                .data(data)
                .error(error)
                .build());
  }

  @DeleteMapping("/{jid}")
  public ResponseEntity<ResponseDto<EmptyResponseDataDto>> deleteJournalById(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      @PathVariable String jid) {
    log.info(requestId, "HTTP DELETE " + path);

    Optional<ResponseErrorDto> error = Optional.empty();

    HttpStatus status;
    MediaType contentType = MediaType.APPLICATION_JSON;

    try {
      journalService.deleteJournalById(jid);

      status = HttpStatus.OK;

    } catch (JournalNotFoundException e) {
      log.info(requestId, e.toString());
      error = Optional.of(ResponseErrorDto.from(ErrorCode.JOURNAL_NOT_FOUND, e.toString(), path));

      status = HttpStatus.NOT_FOUND;
      contentType = MediaType.APPLICATION_PROBLEM_JSON;

    } catch (Exception e) {
      log.error(requestId, e.toString());
      error =
          Optional.of(ResponseErrorDto.from(ErrorCode.INTERNAL_SERVER_ERROR, e.toString(), path));

      status = HttpStatus.INTERNAL_SERVER_ERROR;
      contentType = MediaType.APPLICATION_PROBLEM_JSON;
    }

    OffsetDateTime end = OffsetDateTime.now();
    log.info(
        requestId, "HTTP response took " + Duration.between(timestamp, end).toMillisPart() + "ms");

    return ResponseEntity.status(status)
        .contentType(contentType)
        .body(
            ResponseDto.<EmptyResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(status.value())
                .success(error.isEmpty())
                .data(null)
                .error(error)
                .build());
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto<EmptyResponseDataDto>> handleMethodArgumentNotValidException(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      MethodArgumentNotValidException e) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    MediaType contentType = MediaType.APPLICATION_JSON;

    log.error(requestId, e.toString());
    Optional<ResponseErrorDto> error =
        Optional.of(ResponseErrorDto.from(ErrorCode.BAD_REQUEST, e.toString(), path));

    return ResponseEntity.status(status)
        .contentType(contentType)
        .body(
            ResponseDto.<EmptyResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(status.value())
                .success(false)
                .data(null)
                .error(error)
                .build());
  }
}
