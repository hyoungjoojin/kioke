package io.kioke.feature.dashboard.service;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.WidgetDto;

public interface WidgetProcessor {

  WidgetType type();

  Widget map(WidgetDto widgetDto);

  WidgetDto map(Widget widget);
}
