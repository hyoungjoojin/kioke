package io.kioke.feature.notification.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.feature.notification.dto.NotificationDto;
import io.kioke.feature.notification.dto.response.GetNotificationsResponseDto;
import io.kioke.feature.notification.service.NotificationService;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping("/notifications")
  @ResponseStatus(HttpStatus.OK)
  public GetNotificationsResponseDto getNotifications(@AuthenticatedUser UserDto user) {
    List<NotificationDto> notifications = notificationService.getNotifications(user);
    return new GetNotificationsResponseDto(notifications);
  }
}
