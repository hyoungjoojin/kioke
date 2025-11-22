package io.kioke.feature.dashboard.service.processor;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.WeatherWidget;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequest;
import io.kioke.feature.dashboard.dto.response.GetDashboardResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
class WeatherWidgetProcessor implements WidgetProcessor {

  @Override
  public WidgetType type() {
    return WidgetType.WEATHER;
  }

  @Override
  public List<GetDashboardResponse.Widget> fetchData(List<Widget> widgets) {
    return widgets.stream()
        .filter(widget -> widget.getType() == type() && widget instanceof WeatherWidget)
        .map(widget -> (WeatherWidget) widget)
        .map(
            widget -> {
              var content = new GetDashboardResponse.WeatherWidgetContent();

              return new GetDashboardResponse.Widget(
                  widget.getId(), widget.getType(), widget.getX(), widget.getY(), content);
            })
        .toList();
  }

  @Override
  public List<Widget> getUpdatedWidgets(List<UpdateDashboardRequest.Widget> widgets) {
    return widgets.stream()
        .filter(widget -> widget.type() == type())
        .map(widget -> WeatherWidget.builder().x(widget.x()).y(widget.y()).build())
        .collect(Collectors.toList());
  }
}
