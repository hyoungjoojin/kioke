package io.kioke.feature.dashboard.controller;

import io.kioke.common.auth.AuthenticatedUser;
import io.kioke.feature.dashboard.dto.request.UpdateDashboardRequest;
import io.kioke.feature.dashboard.dto.response.GetDashboardResponse;
import io.kioke.feature.dashboard.service.DashboardService;
import io.kioke.feature.user.dto.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboards")
@RequiredArgsConstructor
public class DashboardController {

  private final DashboardService dashboardService;

  @GetMapping("/me")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public GetDashboardResponse getViewerDashboard(@AuthenticatedUser UserPrincipal user) {
    return dashboardService.getDashboard(user.userId());
  }

  @PutMapping("/me")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateDashboard(
      @AuthenticatedUser UserPrincipal user,
      @RequestBody @Validated UpdateDashboardRequest request) {
    dashboardService.updateDashboard(user.userId(), request);
  }
}
