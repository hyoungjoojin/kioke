package kioke.journal.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kioke.commons.http.HttpResponseBody;
import kioke.journal.dto.request.page.CreatePageRequestBodyDto;
import kioke.journal.dto.response.page.CreatePageResponseBodyDto;
import kioke.journal.dto.response.page.GetPageResponseBodyDto;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.exception.permission.NoEditPermissionsException;
import kioke.journal.model.Journal;
import kioke.journal.model.Page;
import kioke.journal.model.User;
import kioke.journal.service.JournalPermissionService;
import kioke.journal.service.JournalService;
import kioke.journal.service.PageService;
import kioke.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals/{journalId}/pages")
public class PageController {
  @Autowired @Lazy private UserService userService;
  @Autowired @Lazy private JournalService journalService;
  @Autowired @Lazy private JournalPermissionService journalPermissionService;
  @Autowired @Lazy private PageService pageService;

  @PostMapping
  public ResponseEntity<HttpResponseBody<CreatePageResponseBodyDto>> createPage(
      @AuthenticationPrincipal String uid,
      @PathVariable String journalId,
      @Valid @RequestBody CreatePageRequestBodyDto requestBodyDto,
      HttpServletRequest request)
      throws UsernameNotFoundException, JournalNotFoundException, NoEditPermissionsException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(journalId);

    journalPermissionService.checkEditPermissions(user, journal);

    Page page = pageService.createPage(journal, requestBodyDto.getTitle());

    HttpStatus status = HttpStatus.CREATED;
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(HttpResponseBody.success(request, status, CreatePageResponseBodyDto.from(page)));
  }

  @GetMapping("/{pageId}")
  public ResponseEntity<HttpResponseBody<GetPageResponseBodyDto>> getPage(
      @AuthenticationPrincipal String uid,
      @PathVariable String journalId,
      @PathVariable String pageId,
      HttpServletRequest request)
      throws UsernameNotFoundException, JournalNotFoundException {
    User user = userService.getUserById(uid);
    Journal journal = journalService.getJournalById(journalId);

    journalPermissionService.checkReadPermissions(user, journal);

    Page page = pageService.getPage(pageId);

    HttpStatus status = HttpStatus.OK;
    return ResponseEntity.status(status)
        .body(HttpResponseBody.success(request, status, GetPageResponseBodyDto.from(page)));
  }
}
