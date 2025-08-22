package io.kioke.feature.dashboard.controller;

import io.kioke.annotation.AuthenticatedUser;
import io.kioke.feature.dashboard.dto.DashboardDto;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequestDto;
import io.kioke.feature.dashboard.dto.response.GetDashboardResponseDto;
import io.kioke.feature.dashboard.service.DashboardService;
import io.kioke.feature.dashboard.util.DashboardMapper;
import io.kioke.feature.user.dto.UserDto;
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
  public GetDashboardResponseDto getMyDashboard(@AuthenticatedUser UserDto user) {
    DashboardDto dashboard = dashboardService.getDashboard(user);
    return dashboardMapper.toGetDashboardResponse(dashboard);
  }

  @PutMapping("/dashboards/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateDashboard(
      @AuthenticatedUser UserDto user,
      @RequestBody @Validated UpdateDashboardRequestDto requestBody) {
    dashboardService.updateDashboard(user, requestBody);
  }
}
