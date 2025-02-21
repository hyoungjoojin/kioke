package com.kioke.user.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "FRIEND_TABLE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Friend {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "FRIEND_ID", nullable = false)
  private User friend;

  private Boolean isPending;

  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private OffsetDateTime createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private OffsetDateTime lastModified;
}
