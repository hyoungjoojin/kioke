package io.kioke.feature.page.service;

import io.kioke.constant.Permission;
import io.kioke.exception.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.service.JournalAuthService;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.request.CreatePageRequestDto;
import io.kioke.feature.page.dto.request.UpdatePageRequestDto;
import io.kioke.feature.page.repository.PageRepository;
import io.kioke.feature.page.util.PageMapper;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PageService {

  private final JournalAuthService journalAuthService;

  private final PageRepository pageRepository;

  private final PageMapper pageMapper;

  public PageService(
      JournalAuthService journalAuthService, PageRepository pageRepository, PageMapper pageMapper) {
    this.journalAuthService = journalAuthService;
    this.pageRepository = pageRepository;
    this.pageMapper = pageMapper;
  }

  @Transactional(readOnly = true)
  public PageDto getPage(UserDto user, String pageId)
      throws JournalNotFoundException, PageNotFoundException, AccessDeniedException {
    PageDto page =
        pageRepository.findByPageId(pageId).orElseThrow(() -> new PageNotFoundException());

    JournalDto journal = new JournalDto(page.journalId());
    journalAuthService.checkPermissions(user, journal, Permission.READ);

    return page;
  }

  @Transactional
  public PageDto createPage(UserDto user, CreatePageRequestDto request)
      throws JournalNotFoundException, AccessDeniedException {
    JournalDto journal = new JournalDto(request.journalId());
    journalAuthService.checkPermissions(user, journal, List.of(Permission.READ, Permission.EDIT));

    Journal journalReference = new Journal();
    journalReference.setJournalId(request.journalId());

    Page page = new Page();
    page.setJournal(journalReference);
    page.setTitle(request.title());
    page.setDate(request.date());
    page = pageRepository.save(page);

    return pageMapper.toPageDto(request.journalId(), page);
  }

  @Transactional
  public void updatePage(UserDto user, String pageId, UpdatePageRequestDto request)
      throws JournalNotFoundException, AccessDeniedException, PageNotFoundException {
    JournalDto journal = new JournalDto(request.journalId());
    journalAuthService.checkPermissions(user, journal, List.of(Permission.READ, Permission.EDIT));

    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());
    if (request.title() != null) {
      page.setTitle(request.title());
    }

    if (request.content() != null) {
      page.setContent(request.content());
    }

    if (request.date() != null) {
      page.setDate(request.date());
    }

    pageRepository.save(page);
  }
}
