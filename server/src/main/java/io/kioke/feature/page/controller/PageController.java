package io.kioke.feature.page.controller;

import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.PageImageDto;
import io.kioke.feature.page.dto.request.CreatePageRequest;
import io.kioke.feature.page.dto.request.UpdatePageRequest;
import io.kioke.feature.page.service.PageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PageController {

  private final PageService pageService;

  @GetMapping("/pages/{pageId}")
  @ResponseStatus(HttpStatus.OK)
  public PageDto getPage(@PathVariable String pageId) throws PageNotFoundException {
    return pageService.getPageById(pageId);
  }

  @GetMapping("/pages/{pageId}/images")
  @ResponseStatus(HttpStatus.OK)
  public List<PageImageDto> getPageImages(@PathVariable String pageId)
      throws PageNotFoundException {
    return pageService.getPageImages(pageId);
  }

  @PostMapping("/pages")
  @ResponseStatus(HttpStatus.CREATED)
  public PageDto createPage(@RequestBody @Validated CreatePageRequest requestBody) {
    return pageService.createPage(requestBody);
  }

  @PatchMapping("/pages/{pageId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updatePage(
      @PathVariable String pageId, @RequestBody @Validated UpdatePageRequest requestBody)
      throws PageNotFoundException {
    pageService.updatePage(pageId, requestBody);
  }
}
