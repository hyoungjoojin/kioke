package kioke.user.feature.preferences.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import kioke.user.feature.user.domain.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "USER_PREFERENCES_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class UserPreferences {

  @Id
  @Column(name = "USER_ID")
  private String id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "USER_ID", unique = true, nullable = false, updatable = false)
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Theme theme;

  public UserPreferences() {
    this.theme = Theme.SYSTEM;
  }

  public Theme getTheme() {
    return theme;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
