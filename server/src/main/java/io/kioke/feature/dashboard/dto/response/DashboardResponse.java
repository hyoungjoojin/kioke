package io.kioke.feature.dashboard.dto.response;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.content.WidgetContent;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record DashboardResponse(@NotNull List<Widget> widgets) {

  public static record Widget(String id, WidgetType type, int x, int y, WidgetContent content) {}
}
