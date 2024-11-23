package com.kioke.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
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
  @UuidGenerator
  @Column(unique = true, nullable = false)
  private String id;

  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  private String firstName;

  private String lastName;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private OffsetDateTime createdAt;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private OffsetDateTime lastUpdated;

  @Override
  public String getUsername() {
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }
}
