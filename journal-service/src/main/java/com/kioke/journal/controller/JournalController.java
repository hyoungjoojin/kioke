package com.kioke.journal.controller;

import com.kioke.journal.constant.ErrorCode;
import com.kioke.journal.dto.response.ResponseDto;
import com.kioke.journal.dto.response.data.GetJournalResponseDataDto;
import com.kioke.journal.dto.response.error.ResponseErrorDto;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.service.JournalService;
import com.kioke.journal.util.CustomLogger;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals")
public class JournalController {
  @Autowired @Lazy private JournalService journalService;
  private static final CustomLogger log = new CustomLogger(JournalController.class);

  @GetMapping("/{jid}")
  public ResponseEntity<ResponseDto<GetJournalResponseDataDto>> getJournalById(
      HttpServletRequest request, @RequestAttribute String requestId, @PathVariable String jid) {
    String path = request.getRequestURI();
    OffsetDateTime start = OffsetDateTime.now();

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
    log.info(requestId, "HTTP response took " + Duration.between(start, end).toMillisPart() + "ms");

    return ResponseEntity.status(status)
        .contentType(contentType)
        .body(
            ResponseDto.<GetJournalResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(start)
                .status(status.value())
                .success(error.isEmpty())
                .data(data)
                .error(error)
                .build());
  }
}
