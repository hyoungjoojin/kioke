package io.kioke.feature.dashboard.constant;

import io.kioke.feature.dashboard.domain.widget.content.JournalCollectionWidgetContent;
import io.kioke.feature.dashboard.domain.widget.content.SingleJournalWidgetContent;
import io.kioke.feature.dashboard.domain.widget.content.WidgetContent;

public enum WidgetType {
  JOURNAL_COLLECTION(JournalCollectionWidgetContent.class),
  SINGLE_JOURNAL(SingleJournalWidgetContent.class);

  private final Class<? extends WidgetContent> contentClass;

  private WidgetType(Class<? extends WidgetContent> contentClass) {
    this.contentClass = contentClass;
  }

  public Class<? extends WidgetContent> getContentClass() {
    return contentClass;
  }
}
