package com.kioke.journal.controller;

import com.kioke.journal.dto.data.page.CreatePageDto;
import com.kioke.journal.dto.request.page.*;
import com.kioke.journal.dto.response.ResponseDto;
import com.kioke.journal.dto.response.data.page.*;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.model.Page;
import com.kioke.journal.service.PageService;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals/{jid}/pages")
public class PageController {
  @Autowired @Lazy private PageService pageService;

  @PostMapping
  public ResponseEntity<ResponseDto<CreatePageResponseDataDto>> createPage(
      @RequestAttribute String requestId,
      @RequestAttribute String path,
      @RequestAttribute OffsetDateTime timestamp,
      @PathVariable String jid,
      @RequestBody CreatePageRequestBodyDto requestBody)
      throws JournalNotFoundException, Exception {
    Page page = pageService.createPage(jid, CreatePageDto.from(requestBody));
    CreatePageResponseDataDto data = CreatePageResponseDataDto.from(page);

    return ResponseEntity.status(HttpStatus.CREATED)
        .contentType(MediaType.APPLICATION_JSON)
        .body(
            ResponseDto.<CreatePageResponseDataDto>builder()
                .requestId(requestId)
                .path(path)
                .timestamp(timestamp)
                .status(HttpStatus.CREATED.value())
                .success(true)
                .data(Optional.of(data))
                .error(Optional.empty())
                .build());
  }
}
