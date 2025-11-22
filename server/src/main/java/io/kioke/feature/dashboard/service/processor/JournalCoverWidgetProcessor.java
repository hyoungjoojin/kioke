package io.kioke.feature.dashboard.service.processor;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.JournalCoverWidget;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequest;
import io.kioke.feature.dashboard.dto.response.GetDashboardResponse;
import io.kioke.feature.journal.domain.Journal;
import io.kioke.feature.journal.service.JournalService;
import io.kioke.feature.media.service.MediaService;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class JournalCoverWidgetProcessor implements WidgetProcessor {

  private final JournalService journalService;
  private final MediaService mediaService;

  @Override
  public WidgetType type() {
    return WidgetType.JOURNAL_COVER;
  }

  @Override
  public List<GetDashboardResponse.Widget> fetchData(List<Widget> widgets) {
    Stream<JournalCoverWidget> journalCoverWidgets =
        widgets.stream()
            .filter(widget -> widget.getType() == type() && widget instanceof JournalCoverWidget)
            .map(widget -> (JournalCoverWidget) widget);

    Stream<Journal> journals =
        journalService
            .getJournalsByIds(
                journalCoverWidgets.map(widget -> widget.getJournal().getId()).toList())
            .stream();

    Map<String, URL> coverUrls =
        mediaService.getPresignedUrl(journals.map(Journal::getCover).toList());

    Map<String, Journal> journalMap =
        journals.collect(Collectors.toMap(journal -> journal.getId(), journal -> journal));

    return journalCoverWidgets
        .map(
            widget -> {
              String journalId = widget.getJournal().getId();

              Journal journal = journalMap.get(journalId);
              URL coverUrl = coverUrls.get(journalId);

              var content =
                  new GetDashboardResponse.JournalCoverWidgetContent(
                      journal.getId(), coverUrl == null ? null : coverUrl.toExternalForm());

              return new GetDashboardResponse.Widget(
                  widget.getId(), widget.getType(), widget.getX(), widget.getY(), content);
            })
        .toList();
  }

  @Override
  public List<Widget> getUpdatedWidgets(List<UpdateDashboardRequest.Widget> widgets) {
    return widgets.stream()
        .filter(widget -> widget.type() == type())
        .map(
            widget -> {
              var content = (UpdateDashboardRequest.JournalCoverWidgetContent) widget.content();

              Journal journal = Journal.builder().id(content.journalId()).build();

              return JournalCoverWidget.builder()
                  .x(widget.x())
                  .y(widget.y())
                  .journal(journal)
                  .build();
            })
        .collect(Collectors.toList());
  }
}
