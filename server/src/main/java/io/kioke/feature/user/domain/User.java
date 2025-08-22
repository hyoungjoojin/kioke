package io.kioke.feature.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
    name = "USER_TABLE",
    indexes = {@Index(columnList = "email")})
@EntityListeners(AuditingEntityListener.class)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "EMAIL", unique = true, nullable = false)
  private String email;

  @Column(name = "CREATED_AT")
  @CreatedDate
  private Instant createdAt;

  @Column(name = "IS_DELETED", nullable = false)
  private boolean deleted;

  @Column(name = "DELETED_AT")
  private Instant deletedAt;

  protected User() {}

  private User(String userId, String email) {
    this.userId = userId;
    this.email = email;
    this.deleted = false;
  }

  public String getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public static UserBuilder builder() {
    return new UserBuilder();
  }

  public static class UserBuilder {

    private String userId;
    private String email;

    public UserBuilder userId(String userId) {
      this.userId = userId;
      return this;
    }

    public UserBuilder email(String email) {
      this.email = email;
      return this;
    }

    public User build() {
      return new User(userId, email);
    }
  }
}
