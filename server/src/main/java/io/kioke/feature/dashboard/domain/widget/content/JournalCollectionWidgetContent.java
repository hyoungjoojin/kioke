package io.kioke.feature.dashboard.domain.widget.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "JOURNAL_COLLECTION_WIDGET_CONTENT_TABLE")
public class JournalCollectionWidgetContent extends WidgetContent {

  @NotNull
  @Column(name = "COLLECTION_ID", nullable = false)
  private String collectionId;

  public String getCollectionId() {
    return collectionId;
  }
}
