package io.kioke.feature.journal.service;

import io.kioke.exception.auth.AccessDeniedException;
import io.kioke.exception.journal.JournalNotFoundException;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.domain.ShareRequest;
import io.kioke.feature.journal.dto.ShareRequestDto;
import io.kioke.feature.journal.dto.request.ShareJournalRequestDto;
import io.kioke.feature.journal.repository.JournalRepository;
import io.kioke.feature.journal.repository.ShareRequestRepository;
import io.kioke.feature.journal.util.JournalMapper;
import io.kioke.feature.notification.constant.NotificationType;
import io.kioke.feature.notification.domain.content.ShareJournalRequestNotificationContent;
import io.kioke.feature.notification.service.NotificationService;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShareService {

  private final NotificationService notificationService;
  private final JournalRepository journalRepository;
  private final JournalRoleService journalRoleService;
  private final ShareRequestRepository shareRequestRepository;
  private final JournalMapper journalMapper;

  public ShareService(
      NotificationService notificationService,
      JournalRepository journalRepository,
      JournalRoleService journalRoleService,
      ShareRequestRepository shareRequestRepository,
      JournalMapper journalMapper) {
    this.notificationService = notificationService;
    this.journalRepository = journalRepository;
    this.journalRoleService = journalRoleService;
    this.shareRequestRepository = shareRequestRepository;
    this.journalMapper = journalMapper;
  }

  @Transactional
  public ShareRequestDto createShareRequest(
      UserDto userDto, String journalId, ShareJournalRequestDto request)
      throws JournalNotFoundException, AccessDeniedException {
    User requester = User.of(userDto.userId());
    Journal journal =
        journalRepository.findById(journalId).orElseThrow(() -> new JournalNotFoundException());

    if (!journalRoleService.getRole(requester, journal).canShare()) {
      throw new AccessDeniedException();
    }

    User requestee = User.of(request.userId());
    ShareRequest journalShareRequest =
        ShareRequest.builder()
            .journal(journal)
            .requester(requester)
            .requestee(requestee)
            .role(request.role())
            .build();

    journalShareRequest = shareRequestRepository.save(journalShareRequest);

    notificationService.createNotification(
        requestee,
        NotificationType.SHARE_JOURNAL_REQUEST,
        ShareJournalRequestNotificationContent.of(journalShareRequest));

    return journalMapper.toDto(journalShareRequest);
  }

  @Transactional
  public void acceptShareRequest(UserDto user, String journalId, String requestId)
      throws AccessDeniedException {
    ShareRequest request = shareRequestRepository.findById(requestId).orElseThrow();
    if (!user.userId().equals(request.getRequestee().getUserId())) {
      throw new AccessDeniedException();
    }

    User requestee = User.of(user.userId());
    journalRoleService.setRole(requestee, request.getJournal(), request.getRole());
    shareRequestRepository.deleteById(requestId);
  }

  @Transactional
  public void rejectShareRequest(UserDto user, String journalId, String requestId)
      throws AccessDeniedException {
    ShareRequest request = shareRequestRepository.findById(requestId).orElseThrow();
    if (!user.userId().equals(request.getRequestee().getUserId())) {
      throw new AccessDeniedException();
    }

    shareRequestRepository.deleteById(requestId);
  }

  @Transactional
  public void cancelShareRequest(UserDto user, String journalId, String requestId)
      throws JournalNotFoundException, AccessDeniedException {
    ShareRequest request = shareRequestRepository.findById(requestId).orElseThrow();
    if (!user.userId().equals(request.getRequester().getUserId())) {
      throw new AccessDeniedException();
    }

    shareRequestRepository.deleteById(requestId);
  }
}
