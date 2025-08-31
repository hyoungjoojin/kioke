package io.kioke.feature.notification.repository;

import io.kioke.feature.notification.domain.Notification;
import io.kioke.feature.user.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, String> {

  @Query(
      """
      SELECT n FROM Notification n
      LEFT JOIN FETCH n.content c
      LEFT JOIN FETCH TREAT(c AS ShareJournalRequestNotificationContent).shareRequest
      WHERE n.user = :user
      """)
  public List<Notification> findByUser(User user);
}
