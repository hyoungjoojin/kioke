package io.kioke.feature.page.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.dto.request.CreatePageRequest;
import io.kioke.feature.page.dto.request.UpdatePageRequest;
import io.kioke.feature.page.dto.response.CreatePageResponse;
import io.kioke.feature.page.dto.response.PageResponse;
import io.kioke.feature.page.service.PageService;
import io.kioke.feature.page.util.PageMapper;
import io.kioke.feature.user.dto.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
  public PageResponse getPage(@PathVariable String pageId) throws PageNotFoundException {
    Page page = pageService.getPage(pageId);
    return pageMapper.mapToPageResponse(page);
  }

  @PostMapping("/pages")
  @ResponseStatus(HttpStatus.CREATED)
  public CreatePageResponse createPage(
      @AuthenticatedUser UserPrincipal user,
      @RequestBody @Validated CreatePageRequest requestBody) {
    Page page = pageService.createPage(user.userId(), requestBody);
    return pageMapper.mapToCreatePageResponse(page);
  }

  @PatchMapping("/pages/{pageId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updatePage(
      @PathVariable String pageId, @RequestBody @Validated UpdatePageRequest requestBody)
      throws PageNotFoundException {
    pageService.updatePage(pageId, requestBody);
  }
}
