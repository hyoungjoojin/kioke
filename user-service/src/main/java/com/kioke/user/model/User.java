package com.kioke.user.model;

import com.kioke.user.model.Preferences.PreferencesConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
  @Id
  @Column(unique = true, nullable = false)
  private String uid;

  @Column(unique = true)
  private String email;

  private String firstName;

  private String lastName;

  @OneToMany(mappedBy = "friend")
  private List<Friend> inboundFriendships;

  @OneToMany(mappedBy = "user")
  private List<Friend> outboundFriendships;

  @Default
  @Convert(converter = PreferencesConverter.class)
  private Preferences preferences = new Preferences();

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private OffsetDateTime createdAt;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private OffsetDateTime lastUpdated;

  @Override
  public String getUsername() {
    return uid;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }
}
