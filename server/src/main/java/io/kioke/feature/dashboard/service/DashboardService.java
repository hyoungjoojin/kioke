package io.kioke.feature.dashboard.service;

import io.kioke.feature.dashboard.constant.WidgetType;
import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequest;
import io.kioke.feature.dashboard.dto.response.GetDashboardResponse;
import io.kioke.feature.dashboard.repository.DashboardRepository;
import io.kioke.feature.dashboard.service.processor.WidgetProcessor;
import io.kioke.feature.user.domain.User;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

  private final DashboardRepository dashboardRepository;
  private final Map<WidgetType, WidgetProcessor> widgetProcessors;

  public DashboardService(
      DashboardRepository dashboardRepository, List<WidgetProcessor> widgetProcessors) {
    this.dashboardRepository = dashboardRepository;

    this.widgetProcessors =
        widgetProcessors.stream()
            .collect(Collectors.toMap(WidgetProcessor::type, Function.identity()));
  }

  @Transactional(readOnly = true)
  public GetDashboardResponse getDashboard(String userId) {
    Dashboard dashboard =
        dashboardRepository
            .findByUser(User.of(userId))
            .orElseThrow(() -> new IllegalStateException("Dashboard does not exist"));

    List<GetDashboardResponse.Widget> widgets =
        dashboard.getWidgets().stream()
            .collect(Collectors.groupingBy(Widget::getType))
            .entrySet()
            .stream()
            .flatMap(
                entry -> getWidgetProcessor(entry.getKey()).fetchData(entry.getValue()).stream())
            .collect(Collectors.toList());

    return new GetDashboardResponse(widgets);
  }

  @Transactional(rollbackFor = Exception.class)
  public void updateDashboard(String userId, UpdateDashboardRequest request) {
    Dashboard dashboard =
        dashboardRepository
            .findByUser(User.of(userId))
            .orElseThrow(() -> new IllegalStateException("Dashboard does not exist"));

    List<Widget> widgets =
        request.widgets().stream()
            .collect(Collectors.groupingBy(UpdateDashboardRequest.Widget::type))
            .entrySet()
            .stream()
            .flatMap(
                entry ->
                    getWidgetProcessor(entry.getKey()).getUpdatedWidgets(entry.getValue()).stream())
            .map(
                widget -> {
                  widget.setDashboard(dashboard);
                  return widget;
                })
            .toList();

    dashboard.getWidgets().clear();
    dashboard.getWidgets().addAll(widgets);

    dashboardRepository.save(dashboard);
  }

  private WidgetProcessor getWidgetProcessor(WidgetType type) {
    WidgetProcessor processor = widgetProcessors.get(type);
    if (processor == null) {
      throw new IllegalArgumentException("Unsupported widget type: " + type);
    }

    return processor;
  }
}
