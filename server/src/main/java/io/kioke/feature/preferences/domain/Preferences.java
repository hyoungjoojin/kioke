package io.kioke.feature.preferences.domain;

import io.kioke.feature.preferences.constant.Theme;
import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PREFERENCES_TABLE")
public class Preferences {

  @Id
  @Column(name = "USER_ID")
  private String userId;

  @OneToOne
  // @OneToOne(fetch = FetchType.LAZY)
  @MapsId("USER_ID")
  @JoinColumn(name = "USER_ID")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(name = "THEME")
  private Theme theme;

  protected Preferences() {}

  public static Preferences of(String userId) {
    Preferences preferences = new Preferences();
    preferences.userId = userId;
    preferences.theme = Theme.SYSTEM;
    return preferences;
  }

  public Theme getTheme() {
    return theme;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }
}
