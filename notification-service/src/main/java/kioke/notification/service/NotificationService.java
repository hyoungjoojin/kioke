package kioke.notification.service;

import java.util.List;
import kioke.notification.model.Notification;
import kioke.notification.model.User;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  public List<Notification> getNotifications(User user) {
    return user.getIssuedNotifications();
  }
}
