package io.kioke.feature.journal.domain;

import io.kioke.feature.user.domain.User;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "JOURNAL_SHARE_REQUEST_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class JournalShareRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "JOURNAL_ID")
  private Journal journal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REQUESTER_ID")
  private User requester;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REQUESTEE_ID")
  private User requestee;

  @Enumerated(EnumType.STRING)
  @Column(name = "ROLE")
  private JournalRole role;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;
}
