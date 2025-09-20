package io.kioke.feature.journal.domain;

import com.mysema.commons.lang.Assert;
import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "JOURNAL_USER_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class JournalUser {

  @EmbeddedId private JournalUserId id = new JournalUserId();

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId(value = "userId")
  @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId(value = "journalId")
  @JoinColumn(name = "JOURNAL_ID", referencedColumnName = "JOURNAL_ID", nullable = false)
  private Journal journal;

  @Enumerated(EnumType.STRING)
  @Column(name = "JOURNAL_ROLE")
  private JournalRole role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "INVITER_ID")
  private User invitedBy;

  @CreatedDate
  @Column(name = "CREATED_AT")
  private Instant createdAt;

  public User getUser() {
    return user;
  }

  public JournalRole getRole() {
    return role;
  }

  public static JournalUserBuilder builder() {
    return new JournalUserBuilder();
  }

  public static class JournalUserBuilder {

    private User user;
    private Journal journal;
    private JournalRole role;
    private User invitedBy;

    public JournalUserBuilder user(User user) {
      this.user = user;
      return this;
    }

    public JournalUserBuilder journal(Journal journal) {
      this.journal = journal;
      return this;
    }

    public JournalUserBuilder role(JournalRole role) {
      this.role = role;
      return this;
    }

    public JournalUserBuilder invitedBy(User user) {
      this.user = user;
      return this;
    }

    public JournalUser build() {
      Assert.notNull(user, "User must not be null");
      Assert.notNull(journal, "Journal must not be null");
      Assert.notNull(role, "Role must not be null");

      JournalUser journalUser = new JournalUser();
      // journalUser.id.setUserId(user.getUserId());
      // journalUser.id.setJournalId(journal.getJournalId());
      journalUser.user = user;
      journalUser.journal = journal;
      journalUser.role = role;
      journalUser.invitedBy = invitedBy;
      return journalUser;
    }
  }
}
