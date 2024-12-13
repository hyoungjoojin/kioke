package com.kioke.user.exception;

import com.kioke.user.constant.ErrorCode;
import com.kioke.user.dto.response.ResponseDto;
import com.kioke.user.dto.response.data.EmptyResponseDataDto;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ResponseDto<EmptyResponseDataDto>> handleException(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      Exception e) {
    ErrorCode errorCode = ErrorCode.getErrorCodeFromException(e);
    HttpStatus status = errorCode.getStatus();

    ProblemDetail error = GlobalExceptionHandler.getProblemDetail(errorCode);
    error.setInstance(URI.create(path));
    error.setDetail(e.toString());

    return ResponseEntity.status(status)
        .contentType(MediaType.APPLICATION_PROBLEM_JSON)
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

  private static ProblemDetail getProblemDetail(ErrorCode e) {
    ProblemDetail problemDetail = ProblemDetail.forStatus(e.getStatus());
    problemDetail.setType(e.getType());
    problemDetail.setTitle(e.getTitle());

    return problemDetail;
  }
}
