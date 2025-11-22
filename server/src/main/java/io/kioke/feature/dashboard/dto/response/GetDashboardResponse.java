package io.kioke.feature.dashboard.dto.response;

import io.kioke.feature.dashboard.constant.WidgetType;
import java.util.List;

public record GetDashboardResponse(List<Widget> widgets) {

  public static record Widget(String id, WidgetType type, int x, int y, WidgetContent content) {}

  public static interface WidgetContent {}

  public static record AddPageWidgetContent(String journalId) implements WidgetContent {}

  public static record JournalCoverWidgetContent(String title, String coverUrl)
      implements WidgetContent {}

  public static record WeatherWidgetContent() implements WidgetContent {}
}
