package io.kioke.feature.auth.domain;

import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_DETAILS_TABLE")
public class UserDetails {

  @Id
  @Column(name = "USER_ID")
  private String userId;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId("USER_ID")
  @JoinColumn(name = "USER_ID")
  private User user;

  @Column(name = "PASSWORD")
  private String password;

  protected UserDetails() {}

  private UserDetails(String userId, String password) {
    this.userId = userId;
    this.password = password;
  }

  public String getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public static UserDetails from(String userId, String password) {
    return new UserDetails(userId, password);
  }
}
