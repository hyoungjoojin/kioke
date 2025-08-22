package io.kioke.feature.dashboard.service;

import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.dashboard.domain.widget.Widget;
import io.kioke.feature.dashboard.dto.DashboardDto;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequestDto;
import io.kioke.feature.dashboard.repository.DashboardRepository;
import io.kioke.feature.dashboard.util.DashboardMapper;
import io.kioke.feature.user.domain.User;
import io.kioke.feature.user.dto.UserDto;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DashboardService {

  private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

  private final DashboardRepository dashboardRepository;
  private final DashboardMapper dashboardMapper;

  public DashboardService(
      DashboardRepository dashboardRepository, DashboardMapper dashboardMapper) {
    this.dashboardRepository = dashboardRepository;
    this.dashboardMapper = dashboardMapper;
  }

  @Transactional
  public void createDashboard(UserDto user) {
    Dashboard dashboard = Dashboard.from(User.builder().userId(user.userId()).build());
    dashboardRepository.save(dashboard);
    logger.debug("Created dashboard for user {}", user.userId());
  }

  @Transactional(readOnly = true)
  public DashboardDto getDashboard(UserDto user) {
    return dashboardRepository
        .findByUser(User.builder().userId(user.userId()).build())
        .map(dashboardMapper::toDto)
        .orElseThrow(() -> new IllegalStateException("Dashboard does not exist"));
  }

  @Transactional(rollbackFor = Exception.class)
  public void updateDashboard(UserDto user, UpdateDashboardRequestDto request) {
    Dashboard dashboard =
        dashboardRepository
            .findByUser(User.builder().userId(user.userId()).build())
            .orElseThrow(() -> new IllegalStateException("Dashboard does not exist"));

    List<Widget> widgets =
        request.widgets().stream()
            .map(
                widget ->
                    Widget.builder()
                        .dashboard(dashboard)
                        .type(widget.type())
                        .position(widget.x(), widget.y())
                        .content(widget.content())
                        .build())
            .collect(Collectors.toList());

    dashboard.setWidgets(widgets);
    dashboardRepository.save(dashboard);
  }
}
