package io.kioke.feature.notification.service;

import io.kioke.feature.notification.constant.NotificationType;
import io.kioke.feature.notification.domain.Notification;
import io.kioke.feature.notification.domain.content.NotificationContent;
import io.kioke.feature.notification.dto.NotificationDto;
import io.kioke.feature.notification.repository.NotificationRepository;
import io.kioke.feature.notification.util.NotificationMapper;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationMapper notificationMapper;

  public NotificationService(
      NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
    this.notificationRepository = notificationRepository;
    this.notificationMapper = notificationMapper;
  }

  @Transactional(readOnly = true)
  public List<NotificationDto> getNotifications(UserDto userDto) {
    User user = User.of(userDto.userId());

    return notificationRepository.findByUser(user).stream().map(notificationMapper::toDto).toList();
  }

  @Transactional
  public void createNotification(User user, NotificationType type, NotificationContent content) {
    Notification notification =
        Notification.builder().user(user).type(type).content(content).build();
    notificationRepository.save(notification);
  }
}
