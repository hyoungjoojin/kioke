package kioke.notification.dto.response.notification;

import java.util.List;
import kioke.notification.model.Notification;

public record GetNotificationsResponseBodyDto(List<GetNotificationResponseBodyDto> notifications) {

  public GetNotificationsResponseBodyDto(List<GetNotificationResponseBodyDto> notifications) {
    this.notifications = notifications;
  }

  public static GetNotificationsResponseBodyDto from(List<Notification> notifications) {
    return new GetNotificationsResponseBodyDto(
        notifications.stream()
            .map(notification -> GetNotificationResponseBodyDto.from(notification))
            .toList());
  }
}
