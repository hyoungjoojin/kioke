package kioke.notification.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import kioke.commons.dto.message.notification.NotificationMessagePayload;
import kioke.commons.http.HttpResponseBody;
import kioke.notification.dto.response.notification.GetNotificationsResponseBodyDto;
import kioke.notification.model.Notification;
import kioke.notification.model.User;
import kioke.notification.service.NotificationService;
import kioke.notification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

  @Autowired private UserService userService;
  @Autowired private NotificationService notificationService;

  @GetMapping
  public <T extends NotificationMessagePayload>
      ResponseEntity<HttpResponseBody<GetNotificationsResponseBodyDto>> getNotifications(
          @AuthenticationPrincipal String userId, HttpServletRequest request)
          throws UsernameNotFoundException {
    User user = userService.getUserById(userId);

    List<Notification> notifications = notificationService.getNotifications(user);

    HttpStatus status = HttpStatus.OK;
    return ResponseEntity.status(status)
        .body(
            HttpResponseBody.success(
                request, status, GetNotificationsResponseBodyDto.from(notifications)));
  }
}
