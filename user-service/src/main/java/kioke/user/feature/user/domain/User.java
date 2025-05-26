package kioke.user.feature.user.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import kioke.user.feature.preferences.domain.UserPreferences;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

  @Id
  @Column(unique = true, nullable = false)
  private String userId;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String name;

  @OneToOne(fetch = FetchType.LAZY, optional = true, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private UserPreferences userPreferences;

  @CreatedDate private Instant createdAt;

  @LastModifiedDate private Instant lastUpdated;

  public User() {
    this.userId = null;
    this.email = null;
    this.name = null;
    this.userPreferences = null;
  }

  public User(String userId, String email, String name) {
    Objects.requireNonNull(userId, "Parameter userId must not be null.");
    Objects.requireNonNull(email, "Parameter email must not be null");
    Objects.requireNonNull(name, "Parameter name must not be null");

    this.userId = userId;
    this.email = email;
    this.name = name;
    this.userPreferences = null;
  }

  public String getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  @Override
  public String getUsername() {
    return userId;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  public UserPreferences getUserPreferences() {
    return userPreferences;
  }

  public void setUserPreferences(UserPreferences userPreferences) {
    this.userPreferences = userPreferences;
  }
}
