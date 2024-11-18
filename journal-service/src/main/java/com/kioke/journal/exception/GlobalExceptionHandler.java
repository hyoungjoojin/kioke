package com.kioke.journal.exception;

import com.kioke.journal.constant.ErrorCode;
import com.kioke.journal.dto.response.ResponseDto;
import com.kioke.journal.dto.response.data.EmptyResponseDataDto;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler({
    NoResourceFoundException.class,
    MethodArgumentNotValidException.class,
    JournalNotFoundException.class,
    DateTimeParseException.class,
    Exception.class
  })
  public ResponseEntity<ResponseDto<EmptyResponseDataDto>> handleException(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      Exception e) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    MediaType contentType = MediaType.APPLICATION_PROBLEM_JSON;

    log.error("(requestId={}) {}", requestId, e.toString());
    ProblemDetail error = GlobalExceptionHandler.getProblemDetailFromException(e);
    error.setInstance(URI.create(path));
    error.setDetail(e.toString());

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
                .error(Optional.of(error))
                .build());
  }

  private static ProblemDetail getProblemDetailFromException(Exception e) {
    ErrorCode errorCode = ErrorCode.getErrorCodeFromException(e);
    return getProblemDetail(errorCode);
  }

  private static ProblemDetail getProblemDetail(ErrorCode e) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(e.getStatus());
    problemDetail.setType(e.getType());
    problemDetail.setTitle(e.getTitle());

    return problemDetail;
  }
}
