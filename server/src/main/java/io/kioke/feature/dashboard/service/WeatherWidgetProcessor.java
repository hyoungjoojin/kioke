package io.kioke.feature.dashboard.service;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.WeatherWidget;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.WidgetDto;
import io.kioke.feature.dashboard.dto.WidgetDto.WeatherWidgetContent;
import org.springframework.stereotype.Service;

@Service
class WeatherWidgetProcessor implements WidgetProcessor {

  @Override
  public WidgetType type() {
    return WidgetType.WEATHER;
  }

  @Override
  public Widget map(WidgetDto widgetDto) {
    WeatherWidget widget =
        WeatherWidget.builder().type(widgetDto.type()).x(widgetDto.x()).y(widgetDto.y()).build();

    return widget;
  }

  @Override
  public WidgetDto map(Widget widget) {
    return new WidgetDto(
        widget.getType(), widget.getX(), widget.getY(), new WeatherWidgetContent());
  }
}
