package io.kioke.feature.page.service;

import io.kioke.constant.Permission;
import io.kioke.exception.auth.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.service.JournalPermissionService;
import io.kioke.feature.journal.service.JournalService;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.request.CreatePageRequestDto;
import io.kioke.feature.page.dto.request.UpdatePageRequestDto;
import io.kioke.feature.page.repository.PageRepository;
import io.kioke.feature.page.util.PageMapper;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PageService {

  private final JournalService journalService;
  private final JournalPermissionService journalPermissionService;
  private final PageRepository pageRepository;
  private final PageMapper pageMapper;

  public PageService(
      JournalService journalService,
      JournalPermissionService journalPermissionService,
      PageRepository pageRepository,
      PageMapper pageMapper) {
    this.journalService = journalService;
    this.journalPermissionService = journalPermissionService;
    this.pageRepository = pageRepository;
    this.pageMapper = pageMapper;
  }

  @Transactional
  public PageDto createPage(UserDto user, CreatePageRequestDto request)
      throws JournalNotFoundException, AccessDeniedException {
    journalService.getJournal(user, request.journalId());

    Page page =
        Page.builder()
            .journal(Journal.from(request.journalId()))
            .title(request.title())
            .date(request.date())
            .build();
    page = pageRepository.save(page);
    return pageMapper.toDto(page);
  }

  @Transactional(readOnly = true)
  public PageDto getPage(UserDto user, String pageId)
      throws JournalNotFoundException, PageNotFoundException, AccessDeniedException {
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());

    journalPermissionService.checkPermissions(
        User.builder().userId(user.userId()).build(), page.getJournal(), Set.of(Permission.READ));

    return pageMapper.toDto(page);
  }

  @Transactional
  public void updatePage(UserDto user, String pageId, UpdatePageRequestDto request)
      throws JournalNotFoundException, AccessDeniedException, PageNotFoundException {
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());
    journalPermissionService.checkPermissions(
        User.builder().userId(user.userId()).build(), page.getJournal(), Set.of(Permission.EDIT));

    if (request.title() != null) {
      page.changeTitle(request.title());
    }

    if (request.content() != null) {
      page.changeContent(request.content());
    }

    if (request.date() != null) {
      page.changeDate(request.date());
    }

    pageRepository.save(page);
  }
}
