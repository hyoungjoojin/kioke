package io.kioke.feature.friend.domain;

import io.kioke.feature.friend.constant.FriendshipStatus;
import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "FRIENDSHIP_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class Friendship {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "FRIENDSHIP_ID")
  private String id;

  @ManyToOne
  @JoinColumn(name = "USER_A", nullable = false)
  private User userA;

  @ManyToOne
  @JoinColumn(name = "USER_B", nullable = false)
  private User userB;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  private FriendshipStatus status;

  @Column(name = "FRIENDS_SINCE")
  private Instant since;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  public static Friendship createPending(User userA, User userB) {
    Friendship friendship = new Friendship();
    friendship.userA = userA;
    friendship.userB = userB;
    friendship.status = FriendshipStatus.PENDING;
    return friendship;
  }

  public User getRequester() {
    return userA;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
