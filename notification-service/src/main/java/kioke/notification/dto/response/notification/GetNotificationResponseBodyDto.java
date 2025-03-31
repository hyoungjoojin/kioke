package kioke.notification.dto.response.notification;

import java.time.OffsetDateTime;
import kioke.notification.model.Notification;

public record GetNotificationResponseBodyDto(OffsetDateTime createdAt) {

  public static GetNotificationResponseBodyDto from(Notification notification) {
    return new GetNotificationResponseBodyDto(notification.getCreatedAt());
  }
}
