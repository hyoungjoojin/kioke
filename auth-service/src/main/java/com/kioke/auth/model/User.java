package com.kioke.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
  @Column(name = "uid", unique = true, nullable = false)
  private String uid;

  private String email;

  private String password;

  @Override
  public String getUsername() {
    return uid;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }
}
