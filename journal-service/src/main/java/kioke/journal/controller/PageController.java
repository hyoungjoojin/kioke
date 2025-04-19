package kioke.journal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kioke.commons.annotation.HttpResponse;
import kioke.commons.exception.security.AccessDeniedException;
import kioke.journal.dto.request.page.CreatePageRequestBodyDto;
import kioke.journal.dto.request.page.UpdatePageRequestBodyDto;
import kioke.journal.dto.response.page.CreatePageResponseBodyDto;
import kioke.journal.dto.response.page.GetPageResponseBodyDto;
import kioke.journal.exception.journal.JournalNotFoundException;
import kioke.journal.model.Page;
import kioke.journal.service.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journals/{journalId}/pages")
public class PageController {

  private final PageService pageService;

  public PageController(PageService pageService) {
    this.pageService = pageService;
  }

  @GetMapping("/{pageId}")
  @Tag(name = "Get Page By ID")
  @Operation(summary = "Get information about the page with the page ID.")
  @HttpResponse(status = HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public GetPageResponseBodyDto getPageById(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      @PathVariable String pageId)
      throws JournalNotFoundException {

    Page page = pageService.getPageById(userId, journalId, pageId);
    return GetPageResponseBodyDto.from(page);
  }

  @PostMapping
  @Tag(name = "Create Page")
  @Operation(summary = "Create a new page.")
  @HttpResponse(status = HttpStatus.CREATED)
  @PreAuthorize("isAuthenticated()")
  public CreatePageResponseBodyDto createPage(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      @RequestBody CreatePageRequestBodyDto requestBodyDto)
      throws JournalNotFoundException, AccessDeniedException {
    Page page = pageService.createPage(userId, journalId, requestBodyDto.title());
    return CreatePageResponseBodyDto.from(page);
  }

  @PatchMapping("/{pageId}")
  @Tag(name = "Update Page")
  @Operation(summary = "Update page by ID")
  @HttpResponse(status = HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public void updatePage(
      @AuthenticationPrincipal String userId,
      @PathVariable String journalId,
      @PathVariable String pageId,
      @RequestBody UpdatePageRequestBodyDto requestBodyDto)
      throws JournalNotFoundException, AccessDeniedException {
    pageService.updatePage(
        userId, journalId, pageId, requestBodyDto.title(), requestBodyDto.content());
  }
}
