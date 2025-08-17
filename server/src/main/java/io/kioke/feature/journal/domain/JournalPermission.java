package io.kioke.feature.journal.domain;

import io.kioke.feature.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "JOURNAL_PERMISSION_TABLE")
public class JournalPermission {

  @EmbeddedId private JournalPermissionId id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId(value = "userId")
  @JoinColumn(name = "USER_ID", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId(value = "journalId")
  @JoinColumn(name = "JOURNAL_ID", nullable = false)
  private Journal journal;

  @Column(name = "CAN_READ", nullable = false)
  private boolean canRead;

  @Column(name = "CAN_EDIT", nullable = false)
  private boolean canEdit;

  @Column(name = "CAN_DELETE", nullable = false)
  private boolean canDelete;

  public void setUser(User user) {
    this.user = user;
  }

  public void setJournal(Journal journal) {
    this.journal = journal;
  }

  public void setCanRead(boolean canRead) {
    this.canRead = canRead;
  }

  public void setCanEdit(boolean canEdit) {
    this.canEdit = canEdit;
  }

  public void setCanDelete(boolean canDelete) {
    this.canDelete = canDelete;
  }
}
