package io.kioke.feature.dashboard.service;

import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequest;
import io.kioke.feature.dashboard.repository.DashboardRepository;
import io.kioke.feature.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

  private final DashboardRepository dashboardRepository;

  public DashboardService(DashboardRepository dashboardRepository) {
    this.dashboardRepository = dashboardRepository;
  }

  @Transactional(readOnly = true)
  public Dashboard getDashboard(String userId) {
    return dashboardRepository
        .findByUser(User.of(userId))
        .orElseThrow(() -> new IllegalStateException("Dashboard does not exist"));
  }

  @Transactional
  public void createDashboard(User user) {
    Dashboard dashboard = Dashboard.from(user);
    dashboardRepository.save(dashboard);
  }

  @Transactional(rollbackFor = Exception.class)
  public void updateDashboard(String userId, UpdateDashboardRequest request) {
    Dashboard dashboard =
        dashboardRepository
            .findByUser(User.of(userId))
            .orElseThrow(() -> new IllegalStateException("Dashboard does not exist"));

    List<Widget> widgets =
        request.widgets().stream()
            .map(
                widget -> {
                  return Widget.builder()
                      .dashboard(dashboard)
                      .type(widget.type())
                      .position(widget.x(), widget.y())
                      .content(widget.content())
                      .build();
                })
            .collect(Collectors.toList());

    dashboard.setWidgets(widgets);
    dashboardRepository.save(dashboard);
  }
}
