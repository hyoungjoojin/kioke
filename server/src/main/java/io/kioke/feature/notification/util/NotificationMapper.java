package io.kioke.feature.notification.util;

import io.kioke.feature.notification.domain.Notification;
import io.kioke.feature.notification.domain.content.NotificationContent;
import io.kioke.feature.notification.domain.content.ShareJournalRequestNotificationContent;
import io.kioke.feature.notification.dto.NotificationDto;
import io.kioke.feature.notification.dto.NotificationDto.NotificationContentDto;
import io.kioke.feature.notification.dto.NotificationDto.ShareJournalRequestNotificationContentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

  public NotificationDto toDto(Notification notification);

  default NotificationContentDto toDto(NotificationContent content) {
    if (content instanceof ShareJournalRequestNotificationContent shareJournalRequest) {
      return toDto(shareJournalRequest);
    }

    throw new IllegalArgumentException("Invalid content type");
  }

  @Mappings({
    @Mapping(source = "shareRequest.journal.journalId", target = "journalId"),
    @Mapping(source = "shareRequest.journal.title", target = "journalTitle"),
    @Mapping(source = "shareRequest.requester.userId", target = "requesterId"),
    @Mapping(source = "shareRequest.requestee.profile.name", target = "requesterName"),
    @Mapping(source = "shareRequest.role", target = "role"),
    @Mapping(source = "shareRequest.createdAt", target = "sentAt")
  })
  ShareJournalRequestNotificationContentDto toDto(ShareJournalRequestNotificationContent content);
}
