package io.kioke.feature.dashboard.service.processor;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequest;
import io.kioke.feature.dashboard.dto.response.GetDashboardResponse;
import java.util.List;

public interface WidgetProcessor {

  WidgetType type();

  List<GetDashboardResponse.Widget> fetchData(List<Widget> widgets);

  List<Widget> getUpdatedWidgets(List<UpdateDashboardRequest.Widget> widgets);
}
