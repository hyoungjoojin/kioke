package io.kioke.feature.dashboard.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.feature.dashboard.domain.Dashboard;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequest;
import io.kioke.feature.dashboard.dto.response.DashboardResponse;
import io.kioke.feature.dashboard.service.DashboardService;
import io.kioke.feature.dashboard.util.DashboardMapper;
import io.kioke.feature.user.dto.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

  private final DashboardService dashboardService;
  private final DashboardMapper dashboardMapper;

  public DashboardController(DashboardService dashboardService, DashboardMapper dashboardMapper) {
    this.dashboardService = dashboardService;
    this.dashboardMapper = dashboardMapper;
  }

  @GetMapping("/dashboards/me")
  @ResponseStatus(HttpStatus.OK)
  public DashboardResponse getMyDashboard(@AuthenticatedUser UserPrincipal user) {
    Dashboard dashboard = dashboardService.getDashboard(user.userId());
    return dashboardMapper.toDashboardResponse(dashboard);
  }

  @PutMapping("/dashboards/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateDashboard(
      @AuthenticatedUser UserPrincipal user,
      @RequestBody @Validated UpdateDashboardRequest requestBody) {
    dashboardService.updateDashboard(user.userId(), requestBody);
  }
}
