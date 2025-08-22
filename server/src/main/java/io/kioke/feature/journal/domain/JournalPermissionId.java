package io.kioke.feature.journal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class JournalPermissionId implements Serializable {

  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "JOURNAL_ID")
  private String journalId;

  protected JournalPermissionId() {}

  private JournalPermissionId(String userId, String journalId) {
    this.userId = userId;
    this.journalId = journalId;
  }

  public String getUserId() {
    return userId;
  }

  public String getJournalId() {
    return journalId;
  }

  public static JournalPermissionId from(String userId, String journalId) {
    return new JournalPermissionId(userId, journalId);
  }
}
