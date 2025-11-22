package io.kioke.feature.dashboard.service.processor;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.AddPageWidget;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequest;
import io.kioke.feature.dashboard.dto.response.GetDashboardResponse;
import io.kioke.feature.journal.domain.Journal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
class AddPageWidgetProcessor implements WidgetProcessor {

  @Override
  public WidgetType type() {
    return WidgetType.ADD_PAGE;
  }

  @Override
  public List<GetDashboardResponse.Widget> fetchData(List<Widget> widgets) {
    return widgets.stream()
        .filter(widget -> widget.getType() == type() && widget instanceof AddPageWidget)
        .map(widget -> (AddPageWidget) widget)
        .map(
            widget -> {
              var content =
                  new GetDashboardResponse.AddPageWidgetContent(widget.getJournal().getId());

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
              var content = (UpdateDashboardRequest.AddPageWidgetContent) widget.content();

              Journal journal = Journal.builder().id(content.journalId()).build();

              return AddPageWidget.builder().x(widget.x()).y(widget.y()).journal(journal).build();
            })
        .collect(Collectors.toList());
  }
}
