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
  private boolean onboarded;

  @Column(name = "CREATED_AT")
  @CreatedDate
  private Instant createdAt;

  @Column(name = "LAST_MODIFIED_AT")
  @LastModifiedDate
  private Instant lastModifiedAt;

  protected Profile() {}

  private Profile(String userId) {
    this.userId = userId;
    this.onboarded = false;
  }

  public String getUserId() {
    return userId;
  }

  public String getName() {
    return name;
  }

  public boolean isOnboarded() {
    return onboarded;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getLastModifiedAt() {
    return lastModifiedAt;
  }

  public static Profile from(String userId) {
    return new Profile(userId);
  }

  public void changeName(String name) {
    this.name = name;
  }

  public void setOnboardingStatus(boolean onboarded) {
    this.onboarded = onboarded;
  }
}
