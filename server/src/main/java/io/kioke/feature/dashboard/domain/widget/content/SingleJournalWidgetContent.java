package io.kioke.feature.dashboard.domain.widget.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "SINGLE_JOURNAL_WIDGET_CONTENT_TABLE")
public class SingleJournalWidgetContent extends WidgetContent {

  @Column(name = "JOURNAL_ID", nullable = false)
  private String journalId;

  public String getJournalId() {
    return journalId;
  }
}
