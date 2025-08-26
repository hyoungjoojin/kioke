package io.kioke.feature.page.service;

import io.kioke.exception.auth.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.service.JournalRoleService;
import io.kioke.feature.journal.service.JournalService;
import io.kioke.feature.page.domain.Page;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.request.CreatePageRequestDto;
import io.kioke.feature.page.dto.request.UpdatePageRequestDto;
import io.kioke.feature.page.repository.PageRepository;
import io.kioke.feature.page.util.PageMapper;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PageService {

  private final JournalService journalService;
  private final JournalRoleService journalRoleService;
  private final PageRepository pageRepository;
  private final PageMapper pageMapper;

  public PageService(
      JournalService journalService,
      JournalRoleService journalRoleService,
      PageRepository pageRepository,
      PageMapper pageMapper) {
    this.journalService = journalService;
    this.journalRoleService = journalRoleService;
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
  public PageDto getPage(UserDto userDto, String pageId)
      throws JournalNotFoundException, PageNotFoundException {
    User user = User.builder().userId(userDto.userId()).build();
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());

    if (!journalRoleService.getRole(user, page.getJournal()).canRead()) {
      throw new JournalNotFoundException();
    }

    return pageMapper.toDto(page);
  }

  @Transactional
  public void updatePage(UserDto userDto, String pageId, UpdatePageRequestDto request)
      throws AccessDeniedException, PageNotFoundException {
    User user = User.builder().userId(userDto.userId()).build();
    Page page = pageRepository.findById(pageId).orElseThrow(() -> new PageNotFoundException());

    if (!journalRoleService.getRole(user, page.getJournal()).canEdit()) {
      throw new AccessDeniedException();
    }

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
