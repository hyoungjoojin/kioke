package io.kioke.feature.dashboard.dto;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.content.WidgetContent;
import java.util.List;

public record DashboardDto(String id, List<Widget> widgets) {

  public static record Widget(String id, WidgetType type, int x, int y, WidgetContent content) {}
}
