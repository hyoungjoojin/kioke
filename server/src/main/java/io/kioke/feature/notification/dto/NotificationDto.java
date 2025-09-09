package io.kioke.feature.notification.dto;

import io.kioke.feature.journal.constant.Role;
import io.kioke.feature.notification.constant.NotificationStatus;
import io.kioke.feature.notification.constant.NotificationType;
import java.time.Instant;

public record NotificationDto(
    String id, NotificationStatus status, NotificationType type, NotificationContentDto content) {

  public interface NotificationContentDto {}

  public record ShareJournalRequestNotificationContentDto(
      String journalId,
      String journalTitle,
      String requesterId,
      String requesterName,
      Role role,
      Instant sentAt)
      implements NotificationContentDto {}

  public record FriendRequestNotificationContentDto(
      String requesterId, String requesterName, Instant sentAt) implements NotificationContentDto {}
}
