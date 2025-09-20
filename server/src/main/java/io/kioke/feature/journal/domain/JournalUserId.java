package io.kioke.feature.journal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class JournalUserId implements Serializable {

  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "JOURNAL_ID")
  private String journalId;

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setJournalId(String journalId) {
    this.journalId = journalId;
  }
}
