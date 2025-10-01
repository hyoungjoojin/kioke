package io.kioke.feature.page.service;

import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.journal.service.JournalService;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.domain.block.Block;
import io.kioke.feature.page.dto.request.CreatePageRequest;
import io.kioke.feature.page.dto.request.UpdatePageRequest;
import io.kioke.feature.page.repository.PageRepository;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PageService {

  private final PageRepository pageRepository;

  private final JournalService journalService;

  public PageService(PageRepository pageRepository, JournalService journalService) {
    this.pageRepository = pageRepository;
    this.journalService = journalService;
  }

  @Transactional(readOnly = true)
  @PreAuthorize("hasPermission(#pageId, 'page', 'read')")
  public Page getPage(String pageId) throws PageNotFoundException {
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());

    List<String> blockIds = pageRepository.findBlocksInPage(page);
    List<Block> blocks = pageRepository.fetchBlocks(blockIds);

    page.setBlocks(blocks);
    return page;
  }

  @Transactional
  @PreAuthorize("hasPermission(#request.journalId, 'journal', 'update')")
  public Page createPage(String userId, CreatePageRequest request) {
    Page page =
        Page.builder()
            .journal(journalService.getJournalReference(request.journalId()))
            .title(request.title())
            .date(request.date())
            .build();

    page = pageRepository.save(page);
    return page;
  }

  @Transactional
  @PreAuthorize("hasPermission(#pageId, 'page', 'update')")
  public void updatePage(String pageId, UpdatePageRequest request) throws PageNotFoundException {
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());

    if (request.title() != null) {
      page.updateTitle(request.title());
    }

    if (request.date() != null) {
      page.updateDate(request.date());
    }

    pageRepository.save(page);
  }
}
