package io.kioke.feature.page.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.exception.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.request.CreatePageRequestDto;
import io.kioke.feature.page.dto.request.UpdatePageRequestDto;
import io.kioke.feature.page.dto.response.CreatePageResponseDto;
import io.kioke.feature.page.dto.response.GetPageResponseDto;
import io.kioke.feature.page.service.PageService;
import io.kioke.feature.page.util.PageMapper;
import io.kioke.feature.user.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PageController {

  private final PageService pageService;

  private final PageMapper pageMapper;

  public PageController(PageService pageService, PageMapper pageMapper) {
    this.pageService = pageService;
    this.pageMapper = pageMapper;
  }

  @GetMapping("/pages/{pageId}")
  @ResponseStatus(HttpStatus.OK)
  public GetPageResponseDto getPage(@AuthenticatedUser UserDto user, @PathVariable String pageId)
      throws JournalNotFoundException, PageNotFoundException, AccessDeniedException {
    PageDto page = pageService.getPage(user, pageId);
    return pageMapper.toGetPageResponse(page);
  }

  @PostMapping("/pages")
  @ResponseStatus(HttpStatus.CREATED)
  public CreatePageResponseDto createPage(
      @AuthenticatedUser UserDto user, @RequestBody @Validated CreatePageRequestDto requestBody)
      throws JournalNotFoundException, AccessDeniedException {
    PageDto page = pageService.createPage(user, requestBody);
    return pageMapper.toCreatePageResponse(page);
  }

  @PutMapping("/pages/{pageId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updatePage(
      @AuthenticatedUser UserDto user,
      @PathVariable String pageId,
      @RequestBody @Validated UpdatePageRequestDto requestBody)
      throws JournalNotFoundException, AccessDeniedException, PageNotFoundException {
    pageService.updatePage(user, pageId, requestBody);
  }
}
