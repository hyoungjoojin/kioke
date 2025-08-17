package io.kioke.feature.page.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import autoparams.AutoParams;
import io.kioke.constant.Permission;
import io.kioke.exception.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.exception.page.PageNotFoundException;
import io.kioke.feature.journal.dto.JournalDto;
import io.kioke.feature.journal.service.JournalAuthService;
import io.kioke.feature.page.dto.PageDto;
import io.kioke.feature.page.dto.request.CreatePageRequestDto;
import io.kioke.feature.page.dto.request.UpdatePageRequestDto;
import io.kioke.feature.page.repository.PageRepository;
import io.kioke.feature.page.util.PageMapper;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PageServiceTests {

  @InjectMocks private PageService pageService;

  @Mock private JournalAuthService journalAuthService;
  @Mock private PageRepository pageRepository;
  @Mock private PageMapper pageMapper;

  @Test
  @AutoParams
  public void getPage_pageNotFound_throwPageNotFoundException(UserDto user, String pageId) {
    when(pageRepository.findByPageId(pageId)).thenReturn(Optional.empty());

    assertThrows(
        PageNotFoundException.class,
        () -> {
          pageService.getPage(user, pageId);
        });
  }

  @Test
  @AutoParams
  public void getPage_userHasNoPermission_throwJournalNotFoundException(
      UserDto user, PageDto page, String pageId) throws Exception {
    JournalDto journal = new JournalDto(page.journalId());

    when(pageRepository.findByPageId(pageId)).thenReturn(Optional.of(page));
    doThrow(JournalNotFoundException.class)
        .when(journalAuthService)
        .checkPermissions(user, journal, Permission.READ);

    assertThrows(
        JournalNotFoundException.class,
        () -> {
          pageService.getPage(user, pageId);
        });
  }

  @Test
  @AutoParams
  public void createPage_journalDoesNotExist_throwJournalNotFoundException(
      UserDto user, CreatePageRequestDto request) throws Exception {
    JournalDto journal = new JournalDto(request.journalId());

    doThrow(JournalNotFoundException.class)
        .when(journalAuthService)
        .checkPermissions(user, journal, List.of(Permission.READ, Permission.EDIT));

    assertThrows(
        JournalNotFoundException.class,
        () -> {
          pageService.createPage(user, request);
        });
  }

  @Test
  @AutoParams
  public void createPage_userHasNoEditPermission_throwAccessDeniedException(
      UserDto user, CreatePageRequestDto request) throws Exception {
    JournalDto journal = new JournalDto(request.journalId());

    doThrow(AccessDeniedException.class)
        .when(journalAuthService)
        .checkPermissions(user, journal, List.of(Permission.READ, Permission.EDIT));

    assertThrows(
        AccessDeniedException.class,
        () -> {
          pageService.createPage(user, request);
        });
  }

  @Test
  @AutoParams
  public void updatePage_pageDoesNotExist_throwPageNotFoundException(
      UserDto user, String pageId, UpdatePageRequestDto request) throws Exception {
    when(pageRepository.findById(pageId)).thenReturn(Optional.empty());

    assertThrows(
        PageNotFoundException.class,
        () -> {
          pageService.updatePage(user, pageId, request);
        });
  }
}
