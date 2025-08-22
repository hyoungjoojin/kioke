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

  @EmbeddedId private JournalPermissionId id = new JournalPermissionId();

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

  protected JournalPermission() {}

  private JournalPermission(
      User user, Journal journal, boolean canRead, boolean canEdit, boolean canDelete) {
    this.user = user;
    this.journal = journal;
    this.canRead = canRead;
    this.canEdit = canEdit;
    this.canDelete = canDelete;
  }

  public boolean canRead() {
    return canRead;
  }

  public boolean canEdit() {
    return canEdit;
  }

  public boolean canDelete() {
    return canDelete;
  }

  public static JournalPermission ofEmpty() {
    return new JournalPermission(null, null, false, false, false);
  }
}
