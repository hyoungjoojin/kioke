package io.kioke.feature.profile.domain;

import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "PROFILE_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class Profile {

  @Id
  @Column(name = "USER_ID")
  private String userId;

  @OneToOne
  @MapsId("USER_ID")
  @JoinColumn(name = "USER_ID")
  private User user;

  @Column(name = "NAME")
  private String name;

  @Column(name = "IS_ONBOARDED", nullable = false)
  private Boolean onboarded;

  @Column(name = "CREATED_AT")
  @CreatedDate
  private Instant createdAt;

  @Column(name = "LAST_MODIFIED_AT")
  @LastModifiedDate
  private Instant lastModifiedAt;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean isOnboarded() {
    return onboarded;
  }

  public void setOnboarded(boolean onboarded) {
    this.onboarded = onboarded;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getLastModifiedAt() {
    return lastModifiedAt;
  }

  public void setLastModifiedAt(Instant lastModifiedAt) {
    this.lastModifiedAt = lastModifiedAt;
  }
}
