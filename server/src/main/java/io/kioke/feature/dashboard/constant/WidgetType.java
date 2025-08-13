package io.kioke.feature.dashboard.constant;

import io.kioke.feature.dashboard.domain.widget.content.JournalListWidgetContent;
import io.kioke.feature.dashboard.domain.widget.content.WidgetContent;

public enum WidgetType {
  JOURNAL_LIST(JournalListWidgetContent.class);

  private final Class<? extends WidgetContent> contentClass;

  private WidgetType(Class<? extends WidgetContent> contentClass) {
    this.contentClass = contentClass;
  }

  public Class<? extends WidgetContent> getContentClass() {
    return contentClass;
  }
}
