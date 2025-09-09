package io.kioke.feature.notification.domain.content;

import io.kioke.feature.friend.domain.Friendship;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FRIEND_REQUEST_NOTIFICATION_CONTENT_TABLE")
public class FriendRequestNotificationContent extends NotificationContent {

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "FRIENDSHIP_ID")
  private Friendship friendship;

  public static FriendRequestNotificationContent of(Friendship friendship) {
    FriendRequestNotificationContent content = new FriendRequestNotificationContent();
    content.friendship = friendship;
    return content;
  }

  public Friendship getFriendship() {
    return friendship;
  }
}
