package io.kioke.feature.journal.domain;

import io.kioke.feature.journal.constant.Role;
import io.kioke.feature.notification.domain.content.ShareJournalRequestNotificationContent;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

@Entity
@Table(name = "JOURNAL_SHARE_REQUEST_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class ShareRequest {

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
  private Role role;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
  private ShareJournalRequestNotificationContent notification;

  protected ShareRequest() {}

  private ShareRequest(Journal journal, User requester, User requestee, Role role) {
    this.journal = journal;
    this.requester = requester;
    this.requestee = requestee;
    this.role = role;
  }

  public String getId() {
    return id;
  }

  public Journal getJournal() {
    return journal;
  }

  public User getRequester() {
    return requester;
  }

  public User getRequestee() {
    return requestee;
  }

  public Role getRole() {
    return role;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public static ShareRequestBuilder builder() {
    return new ShareRequestBuilder();
  }

  public static class ShareRequestBuilder {

    private Journal journal;
    private User requester;
    private User requestee;
    private Role role;

    public ShareRequestBuilder journal(Journal journal) {
      this.journal = journal;
      return this;
    }

    public ShareRequestBuilder requester(User requester) {
      this.requester = requester;
      return this;
    }

    public ShareRequestBuilder requestee(User requestee) {
      this.requestee = requestee;
      return this;
    }

    public ShareRequestBuilder role(Role role) {
      this.role = role;
      return this;
    }

    public ShareRequest build() {
      Assert.notNull(journal, "Journal must not be null");
      Assert.notNull(requester, "Requester must not be null");
      Assert.notNull(requestee, "Requestee must not be null");
      Assert.notNull(role, "Role must not be null");

      return new ShareRequest(journal, requester, requestee, role);
    }
  }
}
