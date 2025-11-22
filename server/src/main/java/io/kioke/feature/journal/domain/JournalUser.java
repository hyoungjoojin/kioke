package io.kioke.feature.journal.domain;

import io.kioke.feature.user.domain.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "JOURNAL_USER_TABLE")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JournalUser {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "JOURNAL_ID", nullable = false)
  private Journal journal;

  @Enumerated(EnumType.STRING)
  @Column(name = "JOURNAL_ROLE")
  private JournalRole role;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;
}
