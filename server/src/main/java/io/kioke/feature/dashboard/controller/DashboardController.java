package io.kioke.feature.dashboard.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.feature.dashboard.dto.DashboardDto;
import io.kioke.feature.dashboard.dto.UpdateDashboardRequestDto;
import io.kioke.feature.dashboard.service.DashboardService;
import io.kioke.feature.user.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardService dashboardService;

  @GetMapping("/dashboards/me")
  @ResponseStatus(HttpStatus.OK)
  public DashboardDto getMyDashboard(@AuthenticatedUser UserPrincipal user) {
    return dashboardService.getDashboard(user.userId());
  }

  @PutMapping("/dashboards/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateDashboard(
      @AuthenticatedUser UserPrincipal user,
      @RequestBody @Validated UpdateDashboardRequestDto requestBody) {
    dashboardService.updateDashboard(user.userId(), requestBody);
  }
}
