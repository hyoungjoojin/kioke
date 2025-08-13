package io.kioke.feature.dashboard.dto;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.content.WidgetContent;

public record WidgetDto(String id, WidgetType type, int x, int y, WidgetContent content) {}
