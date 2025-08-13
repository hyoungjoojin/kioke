package io.kioke.feature.dashboard.service;

import io.kioke.feature.dashboard.constant.ViewerType;
import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.DashboardDto;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequestDto;
import io.kioke.feature.dashboard.repository.DashboardRepository;
import io.kioke.feature.dashboard.repository.WidgetRepository;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

  private final DashboardRepository dashboardRepository;
  private final WidgetRepository widgetRepository;

  public DashboardService(
      DashboardRepository dashboardRepository, WidgetRepository widgetRepository) {
    this.dashboardRepository = dashboardRepository;
    this.widgetRepository = widgetRepository;
  }

  @Transactional(readOnly = true)
  public DashboardDto getDashboard(UserDto user, ViewerType viewerType) {
    return dashboardRepository.getDashboard(user, viewerType);
  }

  @Transactional
  public void updateDashboard(UserDto user, UpdateDashboardRequestDto request) {
    User userReference = new User();
    userReference.setUserId(user.userId());

    Dashboard dashboard =
        dashboardRepository
            .findByUserAndViewerType(userReference, request.viewerType())
            .orElseGet(
                () -> {
                  Dashboard newDashboard = new Dashboard();
                  newDashboard.setUser(userReference);
                  newDashboard.setViewerType(request.viewerType());
                  newDashboard.setWidgets(new ArrayList<>());
                  return newDashboard;
                });

    List<Widget> widgets =
        request.widgets().stream()
            .map(
                widget -> {
                  Widget w = new Widget();
                  w.setType(widget.type());
                  w.setX(widget.x());
                  w.setY(widget.y());
                  w.setContent(widget.content());
                  w.setDashboard(dashboard);
                  return w;
                })
            .collect(Collectors.toList());
    widgetRepository.saveAll(widgets);

    dashboard.getWidgets().clear();
    dashboard.getWidgets().addAll(widgets);

    dashboardRepository.save(dashboard);
  }
}
