package io.kioke.feature.notification.domain;

import io.kioke.feature.notification.constant.NotificationStatus;
import io.kioke.feature.notification.constant.NotificationType;
import io.kioke.feature.notification.domain.content.NotificationContent;
import io.kioke.feature.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "NOTIFICATION_TABLE")
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "NOTIFICATION_ID")
  private String notificationId;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "NOTIFICATION_TYPE")
  private NotificationType type;

  @Enumerated(EnumType.STRING)
  @Column(name = "NOTIFICATION_STATUS")
  private NotificationStatus status;

  @OneToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "CONTENT")
  private NotificationContent content;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  protected Notification() {}

  private Notification(
      User user, NotificationType type, NotificationStatus status, NotificationContent content) {
    this.user = user;
    this.type = type;
    this.status = status;
    this.content = content;
  }

  public String getId() {
    return notificationId;
  }

  public NotificationStatus getStatus() {
    return status;
  }

  public NotificationType getType() {
    return type;
  }

  public NotificationContent getContent() {
    return content;
  }

  public static NotificationBuilder builder() {
    return new NotificationBuilder();
  }

  public static class NotificationBuilder {

    private User user;
    private NotificationType type;
    private NotificationContent content;

    public NotificationBuilder user(User user) {
      this.user = user;
      return this;
    }

    public NotificationBuilder type(NotificationType type) {
      this.type = type;
      return this;
    }

    public NotificationBuilder content(NotificationContent content) {
      this.content = content;
      return this;
    }

    public Notification build() {
      return new Notification(user, type, NotificationStatus.UNREAD, content);
    }
  }
}
