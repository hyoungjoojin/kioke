package com.kioke.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "USER_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User  {
  @Id
  @Column(unique = true, nullable = false)
  private String uid;

  @Column(unique = true)
  private String email;

  private String firstName;

  private String lastName;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private OffsetDateTime createdAt;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private OffsetDateTime lastUpdated;
}
