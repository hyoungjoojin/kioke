package io.kioke.feature.dashboard.service;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.JournalCoverWidget;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.WidgetDto;
import io.kioke.feature.dashboard.dto.WidgetDto.JournalCoverWidgetContent;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class JournalCoverWidgetProcessor implements WidgetProcessor {

  private final JournalService journalService;

  @Override
  public WidgetType type() {
    return WidgetType.JOURNAL_COVER;
  }

  @Override
  public Widget map(WidgetDto widgetDto) {
    JournalCoverWidgetContent content = (JournalCoverWidgetContent) widgetDto.content();

    Journal journal = journalService.getJournalReference(content.journalId());

    JournalCoverWidget widget =
        JournalCoverWidget.builder()
            .type(widgetDto.type())
            .x(widgetDto.x())
            .y(widgetDto.y())
            .journal(journal)
            .build();

    return widget;
  }

  @Override
  public WidgetDto map(Widget widget) {
    JournalCoverWidget journalCoverWidget = (JournalCoverWidget) widget;

    return new WidgetDto(
        widget.getType(),
        widget.getX(),
        widget.getY(),
        new JournalCoverWidgetContent(journalCoverWidget.getJournal().getJournalId()));
  }
}
