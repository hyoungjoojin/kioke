package io.kioke.feature.page.service;

import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.block.dto.BlockDto;
import io.kioke.feature.block.service.BlockService;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.request.CreatePageRequest;
import io.kioke.feature.page.dto.request.UpdatePageRequest;
import io.kioke.feature.page.repository.PageRepository;
import io.kioke.feature.page.util.PageMapper;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PageService {

  private final BlockService blockService;
  private final PageRepository pageRepository;
  private final PageMapper pageMapper;

  @Transactional(readOnly = true)
  @PreAuthorize("hasPermission(#pageId, 'page', 'read')")
  public PageDto getPageById(String pageId) throws PageNotFoundException {
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());
    List<BlockDto> blocks = blockService.getBlocksInPage(page);
    return pageMapper.map(page, blocks);
  }

  @Transactional
  @PreAuthorize("hasPermission(#request.journalId, 'journal', 'update')")
  public PageDto createPage(CreatePageRequest request) {
    Page page =
        Page.builder()
            .journal(Journal.getReferenceById(request.journalId()))
            .title(request.title())
            .date(request.date())
            .build();
    pageRepository.save(page);

    // blockService.createBlock(
    //     new CreateBlockOperation(page.getPageId(), "", null, BlockType.TEXT_BLOCK, ""));

    return pageMapper.map(page, Collections.emptyList());
  }

  @Transactional
  @PreAuthorize("hasPermission(#pageId, 'page', 'update')")
  public void updatePage(String pageId, UpdatePageRequest request) throws PageNotFoundException {
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());

    if (request.title() != null) {
      page.setTitle(request.title());
    }

    if (request.date() != null) {
      page.setDate(request.date());
    }

    pageRepository.save(page);
  }
}
