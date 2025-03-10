package com.kioke.journal.controller;

import com.kioke.journal.dto.request.page.CreatePageRequestBodyDto;
import com.kioke.journal.dto.response.page.CreatePageResponseBodyDto;
import com.kioke.journal.dto.response.page.GetPageResponseBodyDto;
import com.kioke.journal.exception.journal.JournalNotFoundException;
import com.kioke.journal.exception.permission.AccessDeniedException;
import com.kioke.journal.exception.user.UserNotFoundException;
import com.kioke.journal.model.Journal;
import com.kioke.journal.model.Page;
import com.kioke.journal.model.User;
import com.kioke.journal.service.JournalPermissionService;
import com.kioke.journal.service.JournalService;
import com.kioke.journal.service.PageService;
import com.kioke.journal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pages")
public class PageController {
  @Autowired @Lazy private UserService userService;
  @Autowired @Lazy private JournalService journalService;
  @Autowired @Lazy private JournalPermissionService journalPermissionService;
  @Autowired @Lazy private PageService pageService;

  @PostMapping
  public ResponseEntity<CreatePageResponseBodyDto> createPage(
      @RequestAttribute(required = true, name = "uid") String uid,
      @Valid @RequestBody CreatePageRequestBodyDto requestBodyDto)
      throws UserNotFoundException, JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(requestBodyDto.getJournalId());

    journalPermissionService.checkEditPermissions(user, journal);

    Page page = pageService.createPage(journal, requestBodyDto.getTitle());

    return ResponseEntity.status(HttpStatus.CREATED).body(CreatePageResponseBodyDto.from(page));
  }

  @GetMapping("/{pageId}")
  public ResponseEntity<GetPageResponseBodyDto> getPage(
      @RequestAttribute(required = true, name = "uid") String uid,
      @PathVariable String pageId,
      @RequestParam(required = true) String journalId)
      throws UserNotFoundException, JournalNotFoundException, AccessDeniedException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(journalId);

    journalPermissionService.checkEditPermissions(user, journal);

    Page page = pageService.getPage(pageId);

    return ResponseEntity.status(HttpStatus.CREATED).body(GetPageResponseBodyDto.from(page));
  }
}
