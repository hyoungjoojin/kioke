package io.kioke.feature.journal.domain;

import io.kioke.feature.journal.constant.Role;
import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "JOURNAL_ROLE_TABLE")
public class JournalRole {

  @EmbeddedId private JournalRoleId id = new JournalRoleId();

  @Embeddable
  public static class JournalRoleId implements Serializable {

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "JOURNAL_ID")
    private String journalId;
  }

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId(value = "userId")
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId(value = "journalId")
  @JoinColumn(name = "JOURNAL_ID", nullable = false)
  private Journal journal;

  @Enumerated(EnumType.STRING)
  @Column(name = "JOURNAL_ROLE")
  private Role role;

  protected JournalRole() {}

  private JournalRole(User user, Journal journal, Role role) {
    this.user = user;
    this.journal = journal;
    this.role = role;
  }

  public static JournalRole of(User user, Journal journal, Role role) {
    return new JournalRole(user, journal, role);
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
